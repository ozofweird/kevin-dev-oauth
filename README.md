# kevin-dev-oauth

![](https://img.shields.io/badge/version-0.0.1-brightgreen)

## π Index
- [About](#π-about)
- [Overview](#π-overview)
- [Getting Started](#π-getting-started)
- [Release Notes](#β-release-notes)
- [Issues](#π₯-issues)
- [Contributing](#π₯-contributing)
- [Authors](#π€-authors)
- [License](#π·-license)
- [References](#π-references)
- [Commit Message (Gitmoji)](#βοΈ-commit-messages-gitmoji)

## π About
μμ λ‘κ·ΈμΈμ κ΅¬νν νλ‘μ νΈμλλ€. ν΄λΌμ΄μΈνΈμμ μ λ¬ν΄μ£Όλ 'access_token' νΉμ 'refresh_token'μ κ²μ¦νμ¬ μ μ μ μ λ³΄λ₯Ό κ°μ Έμ€λλ°μ λͺ©μ μ κ°μ§κ³  μμ΅λλ€.
Apple λ‘κ·ΈμΈμ κ²½μ° λ‘μ»¬ νκ²½μμ κ΅¬λμ νμΈμ ν  μ μκΈ° λλ¬Έμ μ°Έκ³ λ§ ν΄μ£ΌμκΈΈ λ°λλλ€.

## π Overview
* Google Login
* Facebook Login
* Apple Login
* Kakao Login
* Naver Login

## π Getting Started

**λ‘κ·ΈμΈ νλ‘μΈμ€**

REST API μλ²μμλ λ‘κ·ΈμΈ νλ©΄ μ°λμ λν μ²λ¦¬κ° νμμμ΅λλ€. μ€μ λ‘ μ°λν  λλ ν΄λΌμ΄μΈνΈμμ λ‘κ·ΈμΈκΉμ§ μλ£νκ³  'access_token'μ API μλ²μ μ λ¬ν΄μ£Όλ νλ‘μΈμ€κ° μ§νλμ΄μΌ ν©λλ€.
μ¬κΈ°μλ νμ€νΈλ₯Ό μν΄ μΉ νκ²½μ κ΅¬μ±νκ³  λ‘κ·ΈμΈ νλ©΄μΌλ‘ λ¦¬λλ μμ ν  μ μλ μ£Όμλ₯Ό μΆλ ₯ν΄λ΄λ ννλ‘ μ§ννμ΅λλ€.

**dependencies**
```
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'
implementation 'com.google.code.gson:gson'
implementation group: 'org.bouncycastle', name: 'bcpkix-jdk15on', version: '1.69'
```

### πΉ Project Structures
```
βββ src
    βββ main
    β   βββ java
    β   β   βββ com
    β   β       βββ example
    β   β           βββ api
    β   β           β   βββ SocialController.java
    β   β           βββ config.oauth
    β   β               βββ dto
    β   β               β   βββ ApplePublicKeyRes.java
    β   β               β   βββ AppleUserInfoRes.java
    β   β               β   βββ FacebookUserInfoRes.java
    β   β               β   βββ GoogleUserInfoRes.java
    β   β               β   βββ KakaoUserInfoRes.java    
    β   β               β   βββ NaverUserInfo.java
    β   β               βββ social
    β   β               β   βββ AppleOAuth.java
    β   β               β   βββ FacebookOAuth.java
    β   β               β   βββ GoogleOAuth.java
    β   β               β   βββ KakaoOAuth.java
    β   β               β   βββ NaverOAuth.java
    β   β               βββ SocialLoginService.java
    β   β               βββ SocialLoginType.java
    β   β               βββ SocialLoginTypeConverter.java
```

## β Release Notes
* 0.0.1
    * νμ€νΈ

## π₯ Issues
- Apple ν ν° κ²μ¦μ λν μ½λ μλ΅
- Apple private key μ μ₯ μμΉμ λν κ³ λ €
- νμ νν΄ μλλ¦¬μ€μ λ°λ₯Έ oauth μ°κ²° ν΄μ μ λν κ³ λ €
    - μ± λ΄μμμ νμ νν΄ (μ μ  νμ νν΄ -> DB μ­μ /λΉνμ±ν -> νΉμ  κΈ°κ° ν -> μ°κ²° ν΄μ )
    - Apple κ³μ μμ μ§μ  νν΄ (Apple νν΄ -> DB μ­μ /λΉνμ±ν)


## π₯ Contributing
ozofweird

## π€ Authors
- [ozofweird](https://github.com/ozofweird) - **Kevin Ahn**

## π· License
ozofweird

## π References
- [kevin-dev-oauth-kakao](https://github.com/ozofweird/kevin-dev-oauth-kakao)
- [kevin-dev-oauth-apple-v1](https://github.com/ozofweird/kevin-dev-oauth-apple-v1)
- [kevin-dev-oauth-apple-v2](https://github.com/ozofweird/kevin-dev-oauth-apple-v2)
- [κ΅¬κΈ λ‘κ·ΈμΈ Access Token λ° Refresh Token...](https://antdev.tistory.com/72)

κ³΅μ λ¬Έμ
- [OAuth 2.0 μ‘μΈμ€ ν ν° μ»κΈ°](https://developers.google.com/identity/protocols/oauth2/web-server#obtainingaccesstokens)
- [λ€μ΄λ² μμ΄λλ‘ λ‘κ·ΈμΈ API λͺμΈ](https://developers.naver.com/docs/login/api/api.md)

---

## βοΈ Commit messages (Gitmoji)

|Gitmoji|Code|Description|
|:-----:|:---:|:--------:|
|π¨|art|νμΌ/μ½λ κ΅¬μ‘° κ°μ |
|π©Ή|adhesive_bandage|κ°λ¨ν μμ |
|β‘οΈ|zap|μ±λ₯ ν₯μ|
|π₯οΈ|fire|μ½λλ νμΌ μ­μ |
|ποΈ|bug|λ²κ·Έ ν΄κ²°|
|ποΈ|ambulance|κΈ΄κΈ μμ |
|β¨οΈ|sparkles|μλ‘μ΄ κΈ°λ₯|
|ποΈ|memo|λ¬Έμ μΆκ°/μμ |
|ποΈ|lipstick|νλ©΄ UI μΆκ°/μμ |
|ποΈ|tada|νλ‘μ νΈ μμ|
|βοΈ|white_check_mark|νμ€νΈ μΆκ°/μμ |
|ποΈ|lock|λ³΄μ μ΄μ μμ |
|ποΈ|bookmark|λ¦΄λ¦¬μ¦/λ²μ  νκ·Έ|
|π§|construction|μμ μ§ν μ€|
|π|green_heart|CI λΉλ μμ |
|β¬οΈ|arrow_down|μμ‘΄μ± λ²μ  λ€μ΄|
|β¬οΈ|arrow_up|μμ‘΄μ± λ²μ  μ|
|π|pushpin|νΉμ  λ²μ  μμ‘΄μ± κ³ μ |
|π·|construction_worker|CI λΉλ μμ€ν μΆκ°/μμ |
|π|chart_with_upwards_trend|λΆμ, μΆμ  μ½λ μΆκ°/μμ |
|β»οΈ|recycle|μ½λ λ¦¬ν©ν λ§|
|β|heavy_plus_sign|μμ‘΄μ± μΆκ°|
|β|heavy_minus_sign|μμ‘΄μ± μ κ±°|
|π§|wrench|μ€μ  νμΌ μΆκ°/μμ |
|π¨|hammer|κ°λ° μ€ν¬λ¦½νΈ μΆκ°/μμ |
|π|globe_with_meridians|λ€κ΅­μ΄ μ§μ|
|π©|poop|μμ’μ μ½λ μΆκ°|
|βͺ|rewind|λ³κ²½ λ΄μ© λλλ¦¬κΈ°|
|π|twisted_rightwards_arrows|λΈλμΉ ν©λ³|
|π½|alien|μΈλΆ API λ³νλ‘ μΈν μμ |
|π|truck|λ¦¬μμ€ μ΄λ/μ΄λ¦ λ³κ²½|
|π₯|boom|λλΌμ΄ κΈ°λ₯ μκ°|
|π±|bento|μμ μΆκ°/μμ |
|π‘|bulb|μ£Όμ μΆκ°/μμ |
|π¬|speech_balloon|μ€νΈλ§ νμΌ μΆκ°/μμ |
|π|card_file_box|λ°μ΄λ²λ² μ΄μ€ κ΄λ ¨ μμ |
|π|loud_sound|λ‘κ·Έ μΆκ°/μμ |
|π|mute|λ‘κ·Έ μ­μ |
|π±|iphone|λ°μν λμμΈ|
|π|see_no_evil|gitignore μΆκ°|
