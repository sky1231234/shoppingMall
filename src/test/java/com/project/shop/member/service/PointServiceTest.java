package com.project.shop.member.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.builder.PointBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Point;
import com.project.shop.member.dto.request.PointRequest;
import com.project.shop.member.dto.request.PointUseRequest;
import com.project.shop.member.dto.response.PointResponse;
import com.project.shop.member.repository.PointRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PointServiceTest extends ServiceCommon {

    @Autowired
    PointService pointService;
    @Autowired
    PointRepository pointRepository;
    Point point;
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

        //point
        point = PointBuilder.createPoint1(member1);
        pointRepository.save(point);
    }

    @Test
    @DisplayName("포인트 전체 조회")
    void pointFindAllTest(){

        //given
        Point point1 = PointBuilder.createPoint2(member1);
        pointRepository.save(point1);
        Point point2 = PointBuilder.usePoint(member1);
        pointRepository.save(point2);

        //when
        PointResponse pointRequest = pointService.pointFindAll(adminId);

        //then
        Assertions.assertThat(pointRepository.findAll().size()).isEqualTo(3);
        Assertions.assertThat(pointRequest.getSumPoint()).isEqualTo(4300);

    }

    @Test
    @DisplayName("포인트 등록")
    void pointCreateTest(){
        //given
        PointRequest pointRequest = PointBuilder.createPointRequest();

        //when
        pointService.create(adminId,pointRequest);

        //then
        Point point = pointRepository.findById(2L)
                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_POINT_TEST"));

        Assertions.assertThat(pointRepository.findSumPoint(1)).isEqualTo(1500);
        Assertions.assertThat(point.getDeadlineDate()).isEqualTo("2025-12-31");
    }

    @Test
    @DisplayName("포인트 사용")
    void pointUse(){
        //given
        PointUseRequest pointUseRequest = PointBuilder.createPointUseRequest();

        //when
        pointService.use(adminId,pointUseRequest);

        //then
        Assertions.assertThat(pointRepository.findSumPoint(1)).isEqualTo(800);
        Assertions.assertThat(pointRepository.findAll().size()).isEqualTo(2);
    }

}
