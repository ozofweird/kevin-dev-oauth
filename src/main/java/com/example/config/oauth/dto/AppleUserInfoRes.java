package com.example.config.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AppleUserInfoRes {

    private String id;
    private String iss;
    private String sub;
    private String aud;
    private String nbf;
    private String exp;
    private String iat;
    private String jti;
}