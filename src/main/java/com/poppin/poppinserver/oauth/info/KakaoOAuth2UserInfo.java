//package com.poppin.poppinserver.oauth.info;
//
//import com.poppin.poppinserver.type.ELoginProvider;
//import lombok.Builder;
//
//import java.util.Map;
//
//@Builder
//public class KakaoOAuth2UserInfo implements OAuth2UserInfo {
//    private final Map<String, Object> attributes;
//    private final String id;
//    private final String email;
//
//    public KakaoOAuth2UserInfo(String accessToken, Map<String, Object> attributes) {
//        // attributes 맵의 kakao_account 키의 값에 실제 attributes 맵이 할당되어 있음
//        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
//        Map<String, Object> kakaoBirthDay = (Map<String, Object>) kakaoAccount.get("kakao_account.birthday");
//        this.attributes = kakaoBirthDay;
//        this.id = ((Long) attributes.get("id")).toString(); // OAuth2 로그인 시 키 값이 된다. 카카오는 "id"이다.
//        this.email = (String) kakaoAccount.get("email");
//
//        this.attributes.put("id", this.id);
//        this.attributes.put("email", this.email);
//    }
//    @Override
//    public ELoginProvider getProvider() {
//        return ELoginProvider.KAKAO;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return this.attributes;
//    }
//
//    @Override
//    public String getId() {
//        return this.id;
//    }
//
//    @Override
//    public String getEmail() {
//        return this.email;
//    }
//}
