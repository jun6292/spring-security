package com.poppin.poppinserver.security.config;

import com.poppin.poppinserver.constant.Constant;
import com.poppin.poppinserver.security.JwtAuthEntryPoint;
import com.poppin.poppinserver.security.filter.JwtAuthenticationFilter;
import com.poppin.poppinserver.security.filter.JwtExceptionFilter;
import com.poppin.poppinserver.security.handler.*;
import com.poppin.poppinserver.security.provider.JwtAuthenticationProvider;
import com.poppin.poppinserver.security.service.CustomUserDetailsService;
import com.poppin.poppinserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final DefaultSignInSuccessHandler defaultSignInSuccessHandler;
    private final DefaultSignInFailureHandler defaultSignInFailureHandler;

    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAceessDeniedHandler;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(Constant.NO_NEED_AUTH_URLS.toArray(new String[0])).permitAll()
                                .anyRequest().authenticated())
                .formLogin(configurer ->
                        configurer
                                .loginProcessingUrl("/api/v1/auth/sign-in")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .successHandler(defaultSignInSuccessHandler)
                                .failureHandler(defaultSignInFailureHandler))
                .logout(configurer ->
                        configurer
                                .logoutUrl("/api/v1/auth/sign-out")
                                .addLogoutHandler(customSignOutProcessHandler)
                                .logoutSuccessHandler(customSignOutResultHandler)
                                .deleteCookies(Constant.AUTHORIZATION_HEADER, Constant.REAUTHORIZATION))
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                                .accessDeniedHandler(jwtAceessDeniedHandler))
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, new JwtAuthenticationProvider(customUserDetailsService, bCryptPasswordEncoder)), LogoutFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)
                .getOrBuild();
    }
}
