package com.example.config.oauth.social;

import com.example.config.oauth.SocialLoginType;

public interface SocialOAuth {

    String getRedirectUri();
    String getAccessToken(String authorizationCode);

    Object getUserInfo(String accessToken);

    default SocialLoginType type() {
        if (this instanceof GoogleOAuth) {
            return SocialLoginType.GOOGLE;
        } else if (this instanceof FacebookOAuth) {
            return SocialLoginType.FACEBOOK;
        } else if (this instanceof AppleOAuth) {
            return SocialLoginType.APPLE;
        } else if (this instanceof NaverOAuth) {
            return SocialLoginType.NAVER;
        } else if (this instanceof KakaoOAuth) {
            return SocialLoginType.KAKAO;
        } else {
            return null;
        }
    }
}
