package com.example.config.oauth.social;

import com.example.config.oauth.dto.ApplePublicKeyRes;
import com.example.config.oauth.dto.AppleUserInfoRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppleOAuth implements SocialOAuth {

    @Value("${custom.config.oauth.social.base_url}")
    private String baseUrl;

    @Value("${custom.config.oauth.social.apple.client_id}")
    private String clientId;

    @Value("${custom.config.oauth.social.apple.team_id}")
    private String teamId;

    // 직접 생성
    private String clientSecret;

    @Value("${custom.config.oauth.social.apple.callback_path}")
    private String callbackPath;

    @Value("${custom.config.oauth.social.apple.authorization_uri}")
    private String authorizationUri;

    @Value("${custom.config.oauth.social.apple.token_uri}")
    private String tokenUri;

    @Value("${custom.config.oauth.social.apple.public_key}")
    private String publicKey;

    private final RestTemplate restTemplate;
    private final Gson gson;

    @Override
    public String getRedirectUri() {

        Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("redirect_uri", baseUrl + callbackPath);
        params.put("response_type", "code%20id_token");
        params.put("response_mode", "fragment");
        params.put("state", "test");
        params.put("nonce", "20B20D-0S8-1K8");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        log.info("--------------------------------------");
        log.info("OAuthRedirectUriParameterString: " + parameterString);

        return authorizationUri + "?" + parameterString;
    }

    @Override
    public String getAccessToken(String authorizationCode) {
        try {
            // 'access_token, expires_in, id_token, refresh_token, token_type' 정보를 받기 위해 필요한 clientSecret 생성
            clientSecret = createClientSecret();
            log.info("--------------------------------------");
            log.info("ClientSecret: " + clientSecret);

            // Set header : Content-type: application/x-www-form-urlencoded
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Set body
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", authorizationCode);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", baseUrl + callbackPath);
            params.add("grant_type", "authorization_code");

            // Set http entity
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            // Request profile
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUri, request, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK)
                return responseEntity.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createClientSecret() {
        try {
            ClassPathResource resource = new ClassPathResource("key/AuthKey_12345678.p8");

            byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String privateKey = new String(bdata, StandardCharsets.UTF_8);

            Reader pemReader = new StringReader(privateKey);
            PEMParser pemParser = new PEMParser(pemReader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();

            Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
            String clientSecret = Jwts.builder()
                    .setHeaderParam("kid", "12345678")
                    .setHeaderParam("alg", "ES256")
                    .setIssuer(teamId)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expirationDate)
                    .setAudience("https://appleid.apple.com")
                    .setSubject("org.example.oauth")
                    .signWith(SignatureAlgorithm.ES256, converter.getPrivateKey(object))
                    .compact();

            log.info("--------------------------------------");
            log.info("clientSecret: " + clientSecret);

            return clientSecret;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getUserInfo(String accessToken) {
        String idToken = accessToken;

        try {
            ApplePublicKeyRes applePublicKeyRes = getApplePublicKey();

            String headerOfIdentityToken = idToken.substring(0, idToken.indexOf("."));
            Map<String, String> header = new ObjectMapper().readValue(new String(Base64.getDecoder().decode(headerOfIdentityToken), StandardCharsets.UTF_8), Map.class);

            ApplePublicKeyRes.Key key = applePublicKeyRes.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                    .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

            byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            Claims claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(idToken).getBody();

            return AppleUserInfoRes.builder()
                    .id(claims.getId())
                    .sub(claims.getSubject())
                    .iss(claims.getIssuer())
                    .aud(claims.getAudience())
                    .exp(claims.getExpiration().toString())
                    .iat(claims.getIssuedAt().toString())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ApplePublicKeyRes getApplePublicKey() {
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(publicKey, String.class);
            if(responseEntity.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(responseEntity.getBody(), ApplePublicKeyRes.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
