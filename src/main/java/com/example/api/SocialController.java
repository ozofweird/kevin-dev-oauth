package com.example.api;

import com.example.config.oauth.SocialLoginService;
import com.example.config.oauth.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/oauth")
@RestController
public class SocialController {

    private final SocialLoginService socialLoginService;

    @GetMapping("/{socialLoginType}")
    public void getRedirectUri(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info("--------------------------------------");
        log.info("SocialLoginType: " + socialLoginType);
        socialLoginService.getRedirectUri(socialLoginType);
    }

    @GetMapping("/{socialLoginType}/callback")
    public String getCallback(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
                              @RequestParam(name = "code") String authorizationCode) {
        log.info("--------------------------------------");
        log.info("AuthorizationCode: " + authorizationCode);
        return socialLoginService.getAccessToken(socialLoginType, authorizationCode);
    }

    @GetMapping("/{socialLoginType}/userInfo")
    public Object getUserInfo(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
                              @RequestParam(name = "access_token") String accessToken) {
        log.info("--------------------------------------");
        log.info("AccessToken: " + accessToken);
        return socialLoginService.getUserInfo(socialLoginType, accessToken);
    }
}
