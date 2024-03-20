package com.poppin.poppinserver.constant;

import java.util.List;

public class Constant {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION = "accessToken";
    public static final String REAUTHORIZATION = "refreshToken";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String USER_ROLE_CLAIM_NAME = "role";
    public static final String USER_EMAIL_CLAIM_NAME = "email";

    public static final List<String> NO_NEED_AUTH_URLS = List.of(
            "/api/v1/auth/sign-up", // 회원가입
            "/api/v1/auth/sign-in", // 로그인
            "/login/oauth2/code/kakao",
            "/oauth2/authorization/kakao",
            "/api/v1/auth/login/kakao"
    );
}
