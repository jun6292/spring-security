package com.poppin.poppinserver.oauth.client;

import com.poppin.poppinserver.constant.Constant;
import com.poppin.poppinserver.oauth.dto.KakaoUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao", url = "https://kapi.kakao.com")
public interface KakaoFeignClient {
    @GetMapping("/v2/user/me")
    KakaoUserDto getKakaoUserInfo(@RequestHeader(Constant.AUTHORIZATION_HEADER) String accessToken);
}
