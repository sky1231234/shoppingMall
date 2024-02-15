package com.project.shop.member.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.DeleteType;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import com.project.shop.member.dto.response.MemberResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

public class MemberServiceTest extends ServiceCommon {

    @Autowired
    MemberService memberService;

    @BeforeEach
    public void before(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        member2 = memberBuilder.signUpAdminMember();
        var memberSave = memberRepository.save(member1);
        var adminSave = memberRepository.save(member2);

        //auth
        Authority auth = memberBuilder.auth(memberSave);
        Authority authAdmin = memberBuilder.authAdmin(adminSave);
        authorityRepository.save(auth);
        authorityRepository.save(authAdmin);

    }

    @Test
    @DisplayName("회원 정보 전체 조회")
    void userFindAll(){

        //given
        //when
        List<MemberResponse> memberResponses = memberService.findAll(member2.getLoginId());

        //then
        Assertions.assertThat(memberResponses.size()).isEqualTo(2);
        Assertions.assertThat(memberResponses.get(0).getLoginId()).isEqualTo("loginId");
    }


    @Test
    @DisplayName("내 정보 조회")
    void userFindDetailTest(){
        //given
        //when
        MemberResponse memberResponse = memberService.detailFind(member1.getLoginId());

        //then
        Assertions.assertThat(memberResponse.getName()).isEqualTo("스프링");

    }

    @Test
    @DisplayName("회원 정보 수정")
    void userUpdateTest(){
        //given
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        MemberUpdateRequest memberUpdateRequest = memberBuilder.memberUpdate();

        //when
        memberService.update(member1.getLoginId(), memberUpdateRequest);

        //then
        Member member = memberRepository.findByLoginId(member1.getLoginId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER_TEST"));

        Assertions.assertThat(member.getPhoneNum()).isEqualTo("9999999");
    }

    @Test
    @DisplayName("회원 탈퇴")
    void userDeleteTest(){
        //given
        //when
        memberService.delete(member1.getLoginId());

        //then
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(1);
    }



}
