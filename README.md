# kevin-dev-oauth

![](https://img.shields.io/badge/version-0.0.1-brightgreen)

## 📋 Index
- [About](#🎉-about)
- [Overview](#👀-overview)
- [Getting Started](#🚀-getting-started)
- [Release Notes](#✅-release-notes)
- [Issues](#🔥-issues)
- [Contributing](#👥-contributing)
- [Authors](#👤-authors)
- [License](#🏷-license)
- [References](#📚-references)
- [Commit Message (Gitmoji)](#✉️-commit-messages-gitmoji)

## 🎉 About
소셜 로그인을 구현한 프로젝트입니다. 클라이언트에서 전달해주는 'access_token' 혹은 'refresh_token'을 검증하여 유저의 정보를 가져오는데에 목적을 가지고 있습니다.
Apple 로그인의 경우 로컬 환경에서 구동을 확인을 할 수 없기 때문에 참고만 해주시길 바랍니다.

## 👀 Overview
* Google Login
* Facebook Login
* Apple Login
* Kakao Login
* Naver Login

## 🚀 Getting Started

**로그인 프로세스**

REST API 서버에서는 로그인 화면 연동에 대한 처리가 필요없습니다. 실제로 연동할 때는 클라이언트에서 로그인까지 완료하고 'access_token'을 API 서버에 전달해주는 프로세스가 진행되어야 합니다.
여기서는 테스트를 위해 웹 환경을 구성하고 로그인 화면으로 리디렉션을 할 수 있는 주소를 출력해내는 형태로 진행했습니다.

**dependencies**
```
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'
implementation 'com.google.code.gson:gson'
implementation group: 'org.bouncycastle', name: 'bcpkix-jdk15on', version: '1.69'
```

### 🔹 Project Structures
```
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           ├── api
    │   │           │   └── SocialController.java
    │   │           └── config.oauth
    │   │               ├── dto
    │   │               │   ├── ApplePublicKeyRes.java
    │   │               │   ├── AppleUserInfoRes.java
    │   │               │   ├── FacebookUserInfoRes.java
    │   │               │   ├── GoogleUserInfoRes.java
    │   │               │   ├── KakaoUserInfoRes.java    
    │   │               │   └── NaverUserInfo.java
    │   │               ├── social
    │   │               │   ├── AppleOAuth.java
    │   │               │   ├── FacebookOAuth.java
    │   │               │   ├── GoogleOAuth.java
    │   │               │   ├── KakaoOAuth.java
    │   │               │   └── NaverOAuth.java
    │   │               ├── SocialLoginService.java
    │   │               ├── SocialLoginType.java
    │   │               └── SocialLoginTypeConverter.java
```

## ✅ Release Notes
* 0.0.1
    * 테스트

## 🔥 Issues
- Apple 토큰 검증에 대한 코드 생략
- Apple private key 저장 위치에 대한 고려
- 회원 탈퇴 시나리오에 따른 oauth 연결 해제에 대한 고려
    - 앱 내에서의 회원 탈퇴 (유저 회원 탈퇴 -> DB 삭제/비활성화 -> 특정 기간 후 -> 연결 해제)
    - Apple 계정에서 직접 탈퇴 (Apple 탈퇴 -> DB 삭제/비활성화)


## 👥 Contributing
ozofweird

## 👤 Authors
- [ozofweird](https://github.com/ozofweird) - **Kevin Ahn**

## 🏷 License
ozofweird

## 📚 References
- [kevin-dev-oauth-kakao](https://github.com/ozofweird/kevin-dev-oauth-kakao)
- [kevin-dev-oauth-apple-v1](https://github.com/ozofweird/kevin-dev-oauth-apple-v1)
- [kevin-dev-oauth-apple-v2](https://github.com/ozofweird/kevin-dev-oauth-apple-v2)
- [구글 로그인 Access Token 및 Refresh Token...](https://antdev.tistory.com/72)

공식 문서
- [OAuth 2.0 액세스 토큰 얻기](https://developers.google.com/identity/protocols/oauth2/web-server#obtainingaccesstokens)
- [네이버 아이디로 로그인 API 명세](https://developers.naver.com/docs/login/api/api.md)

---

## ✉️ Commit messages (Gitmoji)

|Gitmoji|Code|Description|
|:-----:|:---:|:--------:|
|🎨|art|파일/코드 구조 개선|
|🩹|adhesive_bandage|간단한 수정|
|⚡️|zap|성능 향상|
|🔥️|fire|코드나 파일 삭제|
|🐛️|bug|버그 해결|
|🚑️|ambulance|긴급 수정|
|✨️|sparkles|새로운 기능|
|📝️|memo|문서 추가/수정|
|💄️|lipstick|화면 UI 추가/수정|
|🎉️|tada|프로젝트 시작|
|✅️|white_check_mark|테스트 추가/수정|
|🔒️|lock|보안 이슈 수정|
|🔖️|bookmark|릴리즈/버전 태그|
|🚧|construction|작업 진행 중|
|💚|green_heart|CI 빌드 수정|
|⬇️|arrow_down|의존성 버전 다운|
|⬆️|arrow_up|의존성 버전 업|
|📌|pushpin|특정 버전 의존성 고정|
|👷|construction_worker|CI 빌드 시스템 추가/수정|
|📈|chart_with_upwards_trend|분석, 추적 코드 추가/수정|
|♻️|recycle|코드 리팩토링|
|➕|heavy_plus_sign|의존성 추가|
|➖|heavy_minus_sign|의존성 제거|
|🔧|wrench|설정 파일 추가/수정|
|🔨|hammer|개발 스크립트 추가/수정|
|🌐|globe_with_meridians|다국어 지원|
|💩|poop|안좋은 코드 추가|
|⏪|rewind|변경 내용 되돌리기|
|🔀|twisted_rightwards_arrows|브랜치 합병|
|👽|alien|외부 API 변화로 인한 수정|
|🚚|truck|리소스 이동/이름 변경|
|💥|boom|놀라운 기능 소개|
|🍱|bento|에셋 추가/수정|
|💡|bulb|주석 추가/수정|
|💬|speech_balloon|스트링 파일 추가/수정|
|🗃|card_file_box|데이버베이스 관련 수정|
|🔊|loud_sound|로그 추가/수정|
|🔇|mute|로그 삭제|
|📱|iphone|반응형 디자인|
|🙈|see_no_evil|gitignore 추가|
