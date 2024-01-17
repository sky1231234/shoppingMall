package com.project.shop.member.Builder;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.domain.Member;

import java.time.LocalDateTime;

public class PointBuilder {

    public static Point createPoint1(Member member){

        return Point.builder()
                .member(member)
                .point(1000)
                .deadlineDate(LocalDateTime.now())
                .pointType(PointType.적립)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static Point createPoint2(Member member){

        return Point.builder()
                .member(member)
                .point(5500)
                .deadlineDate(LocalDateTime.now())
                .pointType(PointType.적립)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }


}
