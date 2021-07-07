package com.example.config.oauth.social;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppleOAuth implements SocialOAuth {

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
        return null;
    }

    @Override
    public String getAccessToken(String authorizationCode) {
        return null;
    }

    @Override
    public Object getUserInfo(String accessToken) {
        return null;
    }
}
