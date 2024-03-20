package com.poppin.poppinserver.security.info;

import com.poppin.poppinserver.constant.Constant;
import com.poppin.poppinserver.domain.User;
import com.poppin.poppinserver.type.EUserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {

    @Getter
    private final String email;
    private final String password;

    @Getter
    private final EUserRole role;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    public static CustomUserDetails create(User user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Constant.ROLE_PREFIX + user.getRole());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return CustomUserDetails.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .authorities(authorities)
                .build();
    }

//    public static CustomUserDetails create(User user, Map<String, Object> attributes) {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Constant.ROLE_PREFIX + user.getRole());
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(authority);
//        return CustomUserDetails.builder()
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .role(user.getRole())
//                .authorities(authorities)
//                .attributes(attributes)
//                .build();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
