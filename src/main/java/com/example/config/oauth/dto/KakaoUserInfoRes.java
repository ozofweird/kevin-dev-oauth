package com.example.config.oauth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoUserInfoRes {
    private Long id;
    private Properties properties;

    @Getter
    @Setter
    @ToString
    private static class Properties {
        private String nickname;
        private String thumbnail_image;
        private String profile_image;
    }
}