package com.project.shop.member.controller;


import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import com.project.shop.member.dto.response.MemberResponse;
import com.project.shop.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/members")
@RequiredArgsConstructor
@Validated
@Tag( name = "MemberController", description = "[사용자] 회원 API")
public class MemberController {

    private final MemberService memberService;

    //내 정보 조회
    @GetMapping
    public ResponseEntity<MemberResponse> detailFind(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(memberService.detailFind(userDto.getLoginId()));
    }

    //회원정보 수정
    @PutMapping
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto, @RequestBody MemberUpdateRequest memberUpdateRequest){
        memberService.update(userDto.getLoginId(), memberUpdateRequest);
        return ResponseEntity.ok().build();
    }

    //회원정보 탈퇴
    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal UserDto userDto){
        memberService.delete(userDto.getLoginId());
        return ResponseEntity.noContent().build();
    }


}
