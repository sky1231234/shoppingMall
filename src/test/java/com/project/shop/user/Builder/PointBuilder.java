package com.project.shop.user.Builder;

import com.project.shop.user.domain.Point;
import com.project.shop.user.domain.PointType;
import com.project.shop.user.domain.User;

import java.time.LocalDateTime;

public class PointBuilder {

    public static Point createPoint1(User user){

        return Point.builder()
                .users(user)
                .point(1000)
                .deadlineDate(LocalDateTime.now())
                .pointType(PointType.적립)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static Point createPoint2(User user){

        return Point.builder()
                .users(user)
                .point(5500)
                .deadlineDate(LocalDateTime.now())
                .pointType(PointType.적립)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }


}
