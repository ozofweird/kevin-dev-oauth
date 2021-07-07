package com.example.config.oauth.social;

import com.example.config.oauth.dto.FacebookUserInfoRes;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class FacebookOAuth implements SocialOAuth {

    @Value("${custom.config.oauth.social.base_url}")
    private String baseUrl;

    @Value("${custom.config.oauth.social.facebook.client_id}")
    private String clientId;

    @Value("${custom.config.oauth.social.facebook.client_secret}")
    private String clientSecret;

    @Value("${custom.config.oauth.social.facebook.callback_path}")
    private String callbackPath;

    @Value("${custom.config.oauth.social.facebook.authorization_uri}")
    private String authorizationUri;

    @Value("${custom.config.oauth.social.facebook.token_uri}")
    private String tokenUri;

    @Value("${custom.config.oauth.social.facebook.user_info_uri}")
    private String userInfoUri;

    private final RestTemplate restTemplate;
    private final Gson gson;

    @Override
    public String getRedirectUri() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("redirect_uri", baseUrl + callbackPath);
        params.put("state", "{st=state123abc,ds=123456789}");

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
        params.put("client_id", clientId);
        params.put("redirect_uri", baseUrl + callbackPath);
        params.put("client_secret", clientSecret);
        params.put("code", authorizationCode);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUri, params, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK)
                return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "페이스북 로그인 요청 처리 실패";
    }

    @Override
    public FacebookUserInfoRes getUserInfo(String accessToken) {
        /*
        // Case 1) accessToken을 parameter로 보낼 경우
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(userInfoUri + "?access_token=" + accessToken, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(responseEntity.getBody(), FacebookUserInfoRes.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
         */

        // Case 2) accessToken을 header에 담아서 보낼 경우

        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        // Set http entity
        HttpEntity request = new HttpEntity(headers);
        try {
            // Request profile
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    userInfoUri,
                    HttpMethod.GET,
                    request,
                    String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(responseEntity.getBody(), FacebookUserInfoRes.class);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new CustomCommunicationException();
        }
        return null;
//        throw new CustomCommunicationException();
    }

}
