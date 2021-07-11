package com.example.config.oauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverUserInfo {

    private String resultcode;
    private String message;
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private String id;
        private String name;
        private String email;
        private String gender;
        private String age;
        private String birthday;
        private String profile_image;
        private String birthyear;
        private String mobile;
    }

}
