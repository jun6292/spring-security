package com.poppin.poppinserver.domain;

import com.poppin.poppinserver.dto.auth.request.AuthSignUpDto;
import com.poppin.poppinserver.oauth.dto.OAuth2UserInfoDto;
import com.poppin.poppinserver.type.ELoginProvider;
import com.poppin.poppinserver.type.EUserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "is_login", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean isLogin;

    @Column(name = "agreed_to_privacy_policy", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean agreedToPrivacyPolicy;

    @Column(name = "agreed_to_service_terms", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean agreedToServiceTerms;

    @Column(name = "agreed_to_gps", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean agreedToGPS;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private EUserRole role;

    @Column(name = "login_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private ELoginProvider provider;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private Set<Intereste> interestes = new HashSet<>();

    @Builder
    public User(String email, String password, String nickname, String birthDate,
                ELoginProvider eLoginProvider, EUserRole role,
                Boolean agreedToPrivacyPolicy, Boolean agreedToServiceTerms, Boolean agreedToGPS)
    {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.provider = eLoginProvider;
        this.role = role;
        this.agreedToPrivacyPolicy = agreedToPrivacyPolicy;
        this.agreedToServiceTerms = agreedToServiceTerms;
        this.agreedToGPS = agreedToGPS;
        this.createdAt = LocalDate.now();
        this.isLogin = false;
        this.refreshToken = null;
    }

    public static User toUserEntity(AuthSignUpDto authSignUpDto, String encodedPassword, ELoginProvider eLoginProvider) {
        return User.builder()
                .email(authSignUpDto.email())
                .password(encodedPassword)
                .nickname(authSignUpDto.nickname())
                .birthDate(authSignUpDto.birthDate())
                .eLoginProvider(eLoginProvider)
                .role(EUserRole.USER)
                .agreedToPrivacyPolicy(authSignUpDto.agreedToPrivacyPolicy())
                .agreedToServiceTerms(authSignUpDto.agreedToServiceTerms())
                .agreedToGPS(false)
                .build();
    }

    public static User toGuestEntity(OAuth2UserInfoDto oAuth2UserInfoDto, String encodedPassword, ELoginProvider eLoginProvider) {
        return User.builder()
                .email(oAuth2UserInfoDto.email())
                .password(encodedPassword)
                .birthDate("")
                .nickname("")
                .eLoginProvider(eLoginProvider)
                .role(EUserRole.GUEST)
                .agreedToPrivacyPolicy(true)
                .agreedToServiceTerms(true)
                .agreedToGPS(false)
                .build();
    }

    public void register(String nickname, String birthDate) {
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.role = EUserRole.USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void logout() {
        this.isLogin = false;
        this.refreshToken = null;
    }
}
