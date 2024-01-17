package com.project.shop.member.service;

import com.project.shop.global.config.security.domain.TokenResponse;
import com.project.shop.member.Builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.LoginRequest;
import com.project.shop.member.dto.request.SignUpRequest;
import com.project.shop.member.repository.AuthorityRepository;
import com.project.shop.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthorityRepository authRepository;
    Member member1;

    @BeforeEach
    public void before(){
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);

        member1 = memberBuilder.signUpMember();
        memberRepository.save(member1);
        Authority authority = new Authority(0,"user",member1);
        authRepository.save(authority);
    }

    @Test
    @DisplayName("회원가입")
    void userCreateTest(){
        //given
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        Member member = memberBuilder.signUpMember();

        //when
        var result = authService.signUp(memberBuilder.signUpUser());
        //then
        Assertions.assertThat(result).isEqualTo(2);


    }
    @Test
    @DisplayName("로그인")
    void userLoginTest(){

        //given
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        LoginRequest loginRequest = memberBuilder.loginUser();

        TokenResponse tokenResponse = new TokenResponse("Bearer","token");

        //when
        TokenResponse token = authService.login(loginRequest);

        //then
        Assertions.assertThat(token.grantType()).isEqualTo("Bearer");
    }


}
