package com.poppin.poppinserver.oauth.dto;

public record OAuth2UserInfoDto(String email) {
    public static OAuth2UserInfoDto of(String email) {
        return new OAuth2UserInfoDto(email);
    }
}
