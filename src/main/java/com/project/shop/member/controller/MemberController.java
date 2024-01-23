package com.project.shop.member.controller;


import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import com.project.shop.member.dto.response.MemberResponse;
import com.project.shop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;

    //회원정보 조회
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse userDetailFind(@AuthenticationPrincipal UserDto userDto){
        return memberService.userDetailFind(userDto.getLoginId());
    }

    //회원정보 수정
    @PutMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public void userUpdate(@AuthenticationPrincipal UserDto userDto, @RequestBody MemberUpdateRequest memberUpdateRequest){
        memberService.update(userDto.getLoginId(), memberUpdateRequest);
    }

    //회원정보 탈퇴
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userDelete(@AuthenticationPrincipal UserDto userDto){
        memberService.delete(userDto.getLoginId());
    }


}
