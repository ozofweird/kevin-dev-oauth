package com.example.config.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {
    /**
     * Controller에서 전달 받는 공급자 정보의 대문자 값 맵핑
     * @param source @PathVariable
     * @return SocialLoginType
     */
    @Override
    public SocialLoginType convert(String source) {
        return SocialLoginType.valueOf(source.toUpperCase());
    }
}
