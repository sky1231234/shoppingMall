package com.project.shop.member.controller;


import com.project.shop.global.config.security.TokenResponse;
import com.project.shop.member.dto.request.LoginRequest;
import com.project.shop.member.dto.request.SignUpRequest;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import com.project.shop.member.dto.response.MemberResponse;
import com.project.shop.member.service.AuthService;
import com.project.shop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;



    //회원정보 조회
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse userDetailFind(@PathVariable("userId") long userId){
        return memberService.userDetailFind(userId);
    }

    //회원정보 수정
    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void userUpdate(@PathVariable("userId") long userId, @RequestBody MemberUpdateRequest memberUpdateRequest){
        memberService.update(userId, memberUpdateRequest);
    }

    //회원정보 탈퇴
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userDelete(@PathVariable("userId") long userId){
        memberService.delete(userId);
    }


}
