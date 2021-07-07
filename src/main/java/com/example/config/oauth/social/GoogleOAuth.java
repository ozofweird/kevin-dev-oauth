package com.example.config.oauth.social;

import com.example.config.oauth.dto.GoogleUserInfoRes;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class GoogleOAuth implements SocialOAuth {

    @Value("${custom.config.oauth.social.base_url}")
    private String baseUrl;

    @Value("${custom.config.oauth.social.google.client_id}")
    private String clientId;

    @Value("${custom.config.oauth.social.google.client_secret}")
    private String clientSecret;

    @Value("${custom.config.oauth.social.google.callback_path}")
    private String callbackPath;

    @Value("${custom.config.oauth.social.google.authorization_uri}")
    private String authorizationUri;

    @Value("${custom.config.oauth.social.google.token_uri}")
    private String tokenUri;

    @Value("${custom.config.oauth.social.google.user_info_uri}")
    private String userInfoUri;

    private final RestTemplate restTemplate;
    private final Gson gson;

    @Override
    public String getRedirectUri() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile");
        params.put("response_type", "code");
        params.put("client_id", clientId);
        params.put("redirect_uri", baseUrl + callbackPath);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        log.info("--------------------------------------");
        log.info("OAuthRedirectUriParameterString: " + parameterString);

        return authorizationUri + "?" + parameterString;
    }

    @Override
    public String getAccessToken(String authorizationCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", authorizationCode);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", baseUrl + callbackPath);
        params.put("grant_type", "authorization_code");

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUri, params, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK)
                return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "구글 로그인 요청 처리 실패";
    }

    @Override
    public GoogleUserInfoRes getUserInfo(String accessToken) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            // Request profile
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(userInfoUri, request, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(responseEntity.getBody(), GoogleUserInfoRes.class);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new CustomCommunicationException();
        }
        return null;
//        throw new CustomCommunicationException();
    }

}
