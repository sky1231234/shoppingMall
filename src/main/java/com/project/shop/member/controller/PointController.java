package com.project.shop.member.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.response.PointResponse;
import com.project.shop.member.service.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/points")
@RequiredArgsConstructor
@Validated
@Tag( name = "PointController", description = "[사용자] 포인트 API")
public class PointController {

    private final PointService pointService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<PointResponse> findAll(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(pointService.findAll(userDto.getLoginId()));
    }


}
