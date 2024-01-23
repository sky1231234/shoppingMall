package com.project.shop.member.Builder;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.LoginRequest;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import com.project.shop.member.dto.request.SignUpRequest;
import org.apache.juli.logging.Log;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class MemberBuilder {
    static LocalDateTime now = LocalDateTime.now();
    PasswordEncoder passwordEncoder;

    public MemberBuilder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public static Member createUser1(){

        return Member.builder()
                .loginId("loginId")
                .password("password")
                .name("name")
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

    public Member signUpMember(){

        return Member.builder()
                .loginId("loginId")
                .password(passwordEncoder.encode("password"))
                .name("스프링")
                .phoneNum("0100000")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public Member signUpAdminMember(){

        return Member.builder()
                .loginId("adminLogin")
                .password(passwordEncoder.encode("password"))
                .name("admin")
                .phoneNum("0100000")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public SignUpRequest signUpUser(){

        return new SignUpRequest(
                "loginIdSignup",
                "password",
                "nameSignup",
                "0101010");
    }

    public LoginRequest loginUser(){

        return new LoginRequest(
                "loginId",
                "password");

    }

    public Authority auth(Member member){
        return Authority.builder()
                .authName("user").member(member).build();
    }

    public Authority authAdmin(Member member){
        return Authority.builder()
                .authName("admin").member(member).build();
    }
    public MemberUpdateRequest memberUpdate(){

        return new MemberUpdateRequest(
                "loginUpdateId",
                passwordEncoder.encode("updatePassword"),
                "updateName",
                "0000000"
        );
    }
}
