package com.poppin.poppinserver.oauth.info;

import com.poppin.poppinserver.type.ELoginProvider;

import java.util.Map;

public interface OAuth2UserInfo {
    ELoginProvider getProvider();
    Map<String, Object> getAttributes();
    String getId(); // OAuth2 로그인 시 키 값이 된다. 구글은 키 값이 "sub"이고, 네이버는 "response"이고, 카카오는 "id"이다.
    String getEmail();
}
