package com.project.shop.user.Builder;

import com.project.shop.user.domain.User;

import java.time.LocalDateTime;

public class UserBuilder {
    static LocalDateTime now = LocalDateTime.now();

    public static User createUser1(){

        return User.builder()
                .loginId("spring")
                .password("password")
                .name("스프링")
                .phoneNum("0100000")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public static User createUser2(){

        return User.builder()
                .loginId("boot")
                .password("password")
                .name("부트")
                .phoneNum("5555555")
                .insertDate(now)
                .updateDate(now)
                .build();
    }
}
