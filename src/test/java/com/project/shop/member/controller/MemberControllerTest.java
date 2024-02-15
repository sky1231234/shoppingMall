package com.project.shop.member.controller;

import com.project.shop.common.controller.ControllerCommon;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.dto.request.MemberUpdateRequest;
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
public class MemberControllerTest extends ControllerCommon {

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
    @DisplayName("회원정보 조회")
    void userDetailFind() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members"))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(member1.getLoginId()).isEqualTo("loginId");
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("회원정보 수정")
    void userUpdate() throws Exception {

        //given
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        MemberUpdateRequest memberUpdateRequest = memberBuilder.memberUpdate();
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/members")
                        .content(objectMapper.writeValueAsString(memberUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(memberRepository.findByLoginId("loginUpdateId").get().getName()).isEqualTo("updateName");
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("회원정보 탈퇴")
    void delete() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/members"))
                .andExpect(status().isNoContent());

        //then
        Assertions.assertThat(memberRepository.findByLoginId("loginId").get().getDeleteDate()).isNotNull();
    }
}
