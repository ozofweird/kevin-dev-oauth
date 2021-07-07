package com.example.config.oauth;

import com.example.config.oauth.social.SocialOAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SocialLoginService<T> {

    private final List<SocialOAuth> socialOAuthList;
    private final HttpServletResponse httpServletResponse;

    public void getRedirectUri(SocialLoginType socialLoginType) {
        SocialOAuth socialOAuth = this.findSocialOAuthByType(socialLoginType);
        String redirectUri = socialOAuth.getRedirectUri();
        try {
            httpServletResponse.sendRedirect(redirectUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAccessToken(SocialLoginType socialLoginType, String authorizationCode) {
        SocialOAuth socialOAuth = this.findSocialOAuthByType(socialLoginType);
        return socialOAuth.getAccessToken(authorizationCode);
    }

    public Object getUserInfo(SocialLoginType socialLoginType, String accessToken) {
        SocialOAuth socialOAuth = this.findSocialOAuthByType(socialLoginType);
        return socialOAuth.getUserInfo(accessToken);
    }

    private SocialOAuth findSocialOAuthByType(SocialLoginType socialLoginType) {
        return socialOAuthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}
