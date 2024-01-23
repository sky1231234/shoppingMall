package com.project.shop.member.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.response.PointResponse;
import com.project.shop.member.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class PointController {

    private final PointService pointService;

    //포인트 전체 조회
    @GetMapping("/points")
    @ResponseStatus(HttpStatus.OK)
    public PointResponse pointFindAll(@AuthenticationPrincipal UserDto userDto){
        return pointService.pointFindAll(userDto.getLoginId());
    }


}