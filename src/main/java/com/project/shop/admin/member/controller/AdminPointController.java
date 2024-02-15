package com.project.shop.admin.member.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.request.PointRequest;
import com.project.shop.member.dto.request.PointUpdateRequest;
import com.project.shop.member.dto.request.PointUseRequest;
import com.project.shop.member.service.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/admin/points")
@RequiredArgsConstructor
@Validated
@Tag( name = "AdminPointController", description = "[관리자] 포인트 API")
public class AdminPointController {

    private final PointService pointService;


    //포인트 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@AuthenticationPrincipal UserDto userDto, @RequestBody PointRequest pointRequest){
        pointService.create(userDto.getLoginId(), pointRequest);
    }


    //포인트 사용
    @PostMapping("/uses")
    @ResponseStatus(HttpStatus.CREATED)
    public void use(@AuthenticationPrincipal UserDto userDto, @RequestBody PointUseRequest pointUseRequest){
        pointService.use(userDto.getLoginId(), pointUseRequest);
    }


}
