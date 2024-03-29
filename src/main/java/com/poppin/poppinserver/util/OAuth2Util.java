package com.poppin.poppinserver.util;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.poppin.poppinserver.constant.Constant;
import com.poppin.poppinserver.oauth.dto.OAuth2UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class OAuth2Util {
    private final RestTemplate restTemplate = new RestTemplate();

    public OAuth2UserInfoDto getKakaoUserInfo(String accessToken) {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(Constant.AUTHORIZATION_HEADER, Constant.BEARER_PREFIX + accessToken);
        httpHeaders.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<?> kakaoProfileRequest = new HttpEntity<>(httpHeaders);
        log.info("kakaoProfileRequest" + kakaoProfileRequest);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JsonElement element = JsonParser.parseString(response.getBody());

        return OAuth2UserInfoDto.of(element.getAsJsonObject().getAsJsonObject("kakao_account").get("email").getAsString());
    }
}
