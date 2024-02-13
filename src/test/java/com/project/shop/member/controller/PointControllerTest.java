package com.project.shop.member.controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.shop.common.controller.ControllerCommon;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.builder.PointBuilder;
import com.project.shop.member.domain.*;
import com.project.shop.member.dto.request.*;
import com.project.shop.member.repository.PointRepository;
import com.project.shop.mock.WithCustomMockUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PointControllerTest extends ControllerCommon {

    @Autowired
    PointRepository pointRepository;
    Point point;


    @BeforeEach
    void beforeEach(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        var memberSave = memberRepository.save(member1);
        member2 = memberBuilder.signUpAdminMember();
        var memberAdminSave = memberRepository.save(member2);

        Authority auth = memberBuilder.auth(memberSave);
        Authority authAdmin = memberBuilder.authAdmin(memberAdminSave);
        authorityRepository.save(auth);
        authorityRepository.save(authAdmin);

        //point
        point = PointBuilder.createPoint1(member1);
        pointRepository.save(point);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("포인트 전체 조회")
    void pointFindAll() throws Exception {
        //given
        Point point = PointBuilder.createPoint2(member1);
        pointRepository.save(point);

        Point use = PointBuilder.usePoint(member1);
        pointRepository.save(use);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/points"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("포인트 등록")
    void pointCreate() throws Exception {

        //given
        PointRequest pointRequest = PointBuilder.createPointRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/points")
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(pointRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //then
        Assertions.assertThat(pointRepository.findById(2L).get().getPoint()).isEqualTo(500);
        Assertions.assertThat(pointRepository.findById(2L).get().getPointType()).isEqualTo(PointType.적립);

    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("포인트 사용")
    void pointUse() throws Exception {

        Point point = PointBuilder.createPoint2(member1);
        pointRepository.save(point);
        //given
        PointUseRequest pointUseRequest = PointBuilder.createPointUseRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/points/uses")
                        .content(objectMapper.writeValueAsString(pointUseRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        //then
        Assertions.assertThat(pointRepository.findAll().size()).isEqualTo(3);
        Assertions.assertThat(pointRepository.findById(3L).get().getPointType()).isEqualTo(PointType.사용);

    }



}
