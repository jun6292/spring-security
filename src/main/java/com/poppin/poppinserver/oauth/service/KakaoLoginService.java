package com.poppin.poppinserver.oauth.service;

import com.poppin.poppinserver.constant.Constant;
import com.poppin.poppinserver.oauth.client.KakaoFeignClient;
import com.poppin.poppinserver.oauth.dto.KakaoUserDto;
import com.poppin.poppinserver.oauth.dto.OAuth2UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginService {
    private final KakaoFeignClient kakaoFeignClient;
    public OAuth2UserInfoDto getUserInfo(String accessToken) {
        KakaoUserDto kakaoUserDto = kakaoFeignClient.getKakaoUserInfo(accessToken);
        return OAuth2UserInfoDto.of(kakaoUserDto.kakaoAccount().email());
    }
}
