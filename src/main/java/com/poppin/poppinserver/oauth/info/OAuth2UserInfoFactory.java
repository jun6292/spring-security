//package com.poppin.poppinserver.oauth.info;
//
//
//import com.poppin.poppinserver.type.ELoginProvider;
//
//import java.util.Map;
//
//public class OAuth2UserInfoFactory {
//    // 해당 로그인인 서비스가 kakao인지 google인지 구분하여, 알맞게 매핑을 해주도록 합니다.
//    // 여기서 registrationId는 OAuth2 로그인을 처리한 서비스 명("google","kakao","naver"..)이 되고,
//    // userNameAttributeName은 해당 서비스의 map의 키값이 되는 값이됩니다. {google="sub", kakao="id", naver="response"}
//    public static OAuth2UserInfo getOAuth2UserInfo(ELoginProvider loginProvider, String accessToken, Map<String, Object> attributes) {
//        return switch (loginProvider) {
//            case KAKAO -> new KakaoOAuth2UserInfo(accessToken, attributes);
//            default -> throw new IllegalArgumentException("Unsupported Login Provider");
//        };
//    }
//}
