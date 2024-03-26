package com.project.shop.member.controller;


import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import com.project.shop.member.dto.response.MemberResponse;
import com.project.shop.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<MemberResponse> detailFind(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(memberService.detailFind(userDto.getLoginId()));
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @PutMapping
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto, @RequestBody MemberUpdateRequest memberUpdateRequest){
        memberService.update(userDto.getLoginId(), memberUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal UserDto userDto){
        memberService.delete(userDto.getLoginId());
        return ResponseEntity.noContent().build();
    }


}
