package com.example.config.oauth.social;

import com.example.config.oauth.dto.NaverUserInfo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class NaverOAuth implements SocialOAuth {

    @Value("${custom.config.oauth.social.base_url}")
    private String baseUrl;

    @Value("${custom.config.oauth.social.naver.client_id}")
    private String clientId;

    @Value("${custom.config.oauth.social.naver.client_secret}")
    private String clientSecret;

    @Value("${custom.config.oauth.social.naver.callback_path}")
    private String callbackPath;

    @Value("${custom.config.oauth.social.naver.authorization_uri}")
    private String authorizationUri;

    @Value("${custom.config.oauth.social.naver.token_uri}")
    private String tokenUri;

    @Value("${custom.config.oauth.social.naver.user_info_uri}")
    private String userInfoUri;

    private final RestTemplate restTemplate;
    private final Gson gson;

    @Override
    public String getRedirectUri() {
        Map<String, Object> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", clientId);
        params.put("redirect_uri", baseUrl + callbackPath);
        params.put("state", "test");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        log.info("--------------------------------------");
        log.info("OAuthRedirectUriParameterString: " + parameterString);

        return authorizationUri + "?" + parameterString;
    }

    @Override
    public String getAccessToken(String authorizationCode) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", baseUrl + callbackPath);
        params.add("grant_type", "authorization_code");
        params.add("state", "test");
        params.add("code", authorizationCode);
        params.add("sercive_provider", "NAVER");

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public Object getUserInfo(String accessToken) {
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
                return gson.fromJson(responseEntity.getBody(), NaverUserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new CustomCommunicationException();
        }
        return null;
//        throw new CustomCommunicationException();
    }
}
