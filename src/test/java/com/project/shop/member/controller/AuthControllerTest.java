package com.project.shop.member.controller;

import com.project.shop.common.controller.ControllerCommon;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.dto.request.LoginRequest;
import com.project.shop.member.dto.request.SignUpRequest;
import com.project.shop.mock.WithCustomMockUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest extends ControllerCommon {


    @BeforeEach
    void beforeEach(){
        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        var memberSave = memberRepository.save(member1);
        Authority auth = memberBuilder.auth(memberSave);
        authorityRepository.save(auth);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("회원가입")
    void userDetailFind() throws Exception {
        //given
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        SignUpRequest signUpRequest = memberBuilder.signUpUser();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //then
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("로그인")
    void userUpdate() throws Exception {

        //given
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        LoginRequest loginRequest = memberBuilder.loginUser();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then

    }

}
