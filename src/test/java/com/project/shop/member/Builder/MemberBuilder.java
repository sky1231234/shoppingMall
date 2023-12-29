package com.project.shop.member.Builder;

import com.project.shop.member.domain.Member;

import java.time.LocalDateTime;

public class MemberBuilder {
    static LocalDateTime now = LocalDateTime.now();

    public static Member createUser1(){

        return Member.builder()
                .loginId("spring")
                .password("password")
                .name("스프링")
                .phoneNum("0100000")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public static Member createUser2(){

        return Member.builder()
                .loginId("boot")
                .password("password")
                .name("부트")
                .phoneNum("5555555")
                .insertDate(now)
                .updateDate(now)
                .build();
    }
}
