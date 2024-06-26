package com.gomdoly.book.springboot.config.auth;

import com.gomdoly.book.springboot.config.auth.dto.OAuthAttributes;
import com.gomdoly.book.springboot.config.auth.dto.SessionUser;
import com.gomdoly.book.springboot.domain.user.User;
import com.gomdoly.book.springboot.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스를 구분한다
                                                              // 현재는 필요 없지만 나중에 네이버 로그인 연동 시 필요
        String userNameAttributeName = userRequest            // OAuth2 로그인 진행 시 키가 되는 필드값, PK와 같은 역할
                .getClientRegistration().getProviderDetails() // 이후에 네이버 로그인과 구글 로그인을 동시 지원할 때 사용
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes          // Service를 통해 가져온 OAuth2User의 attribute를 담을 클래스
                .of(registrationId,userNameAttributeName,     // 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용
                        oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user",new SessionUser(user)); // 세션에 사용자 정보를 저장하기 위한 DTO 클래스

        return new DefaultOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes()
                        ,attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }

}
