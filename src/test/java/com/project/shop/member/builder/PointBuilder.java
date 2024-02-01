package com.project.shop.member.builder;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.PointRequest;
import com.project.shop.member.dto.request.PointUpdateRequest;
import com.project.shop.member.dto.request.PointUseRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PointBuilder {

    public static Point createPoint1(Member member){

        return Point.builder()
                .member(member)
                .point(1000)
                .deadlineDate(LocalDate.of(2024,12,21))
                .pointType(PointType.적립)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static Point createPoint2(Member member){

        return Point.builder()
                .member(member)
                .point(5500)
                .deadlineDate(LocalDate.of(2024,12,21)
                )
                .pointType(PointType.적립)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static Point usePoint(Member member){

        return Point.builder()
                .member(member)
                .point(-2200)
                .deadlineDate(LocalDate.of(2024,12,21))
                .pointType(PointType.사용)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static PointRequest createPointRequest(){

        return new PointRequest(
                1,
                500,
                LocalDate.of(2025,12,31)
        );
    }

    public static PointUseRequest createPointUseRequest(){

        return new PointUseRequest(
                1L,
                200
        );
    }

}
