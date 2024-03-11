package com.project.shop.member.service;

import com.project.shop.global.config.security.filter.JwtFilter;
import com.project.shop.global.config.security.domain.TokenResponse;
import com.project.shop.global.config.security.TokenProvider;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.LoginRequest;
import com.project.shop.member.dto.request.SignUpRequest;
import com.project.shop.member.repository.AuthorityRepository;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;

    //회원가입
    @Transactional
    public long signUp(SignUpRequest signUpRequest){

        //loginId 중복 확인
        if(memberRepository.findByLoginId(signUpRequest.loginId()).isPresent())
            throw new RuntimeException("ALREADY_EXIST_LOGIN_ID");

        var user = signUpRequest.toEntity(passwordEncoder);
        var saveUser = memberRepository.save(user);

        Authority authority = Authority.builder()
                .authName(signUpRequest.auth())
                .member(saveUser)
                .build();

        authorityRepository.save(authority);

        return user.getUserId();
    }

    //로그인
    @Transactional
    public TokenResponse login(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.loginId(), loginRequest.password());

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        TokenResponse jwtToken = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();

//      // response header에 jwt token에 넣어줌
        httpHeaders.add("Authorization", "Bearer" + jwtToken.accessToken());

        return jwtToken;
    }

    public void authCheck(String loginId){

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
        Authority authority = authorityRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_AUTH"));

        if(authority.getAuthName().equals("user"))
            throw new RuntimeException("ONLY_ADMIN");
    }
}
