package com.project.shop.member.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.global.config.security.domain.TokenResponse;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.LoginRequest;
import com.project.shop.member.dto.request.SignUpRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class AuthServiceTest extends ServiceCommon {

    @Autowired
    AuthService authService;

    @BeforeEach
    public void before(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        var memberSave = memberRepository.save(member1);

        //auth
        Authority auth = memberBuilder.auth(memberSave);
        authorityRepository.save(auth);
    }

    @Test
    @DisplayName("회원가입")
    void memberCreateTest(){
        //given
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        SignUpRequest signUpUser = memberBuilder.signUpUser();

        //when
        var signUpId = authService.signUp(signUpUser);

        //then
        Member member = memberRepository.findById(signUpId)
                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
        Authority authority = authorityRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_AUTH"));;

        Assertions.assertThat(member.getLoginId()).isEqualTo("loginIdSignup");
        Assertions.assertThat(authority.getAuthName()).isEqualTo("user");


    }
    @Test
    @DisplayName("로그인")
    void memberLoginTest(){

        //given
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        LoginRequest loginRequest = memberBuilder.loginUser();

        //when
        TokenResponse token = authService.login(loginRequest);

        //then
        Assertions.assertThat(token.grantType()).isEqualTo("Bearer");
    }


}
