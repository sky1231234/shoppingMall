package com.project.shop.member.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.request.PointRequest;
import com.project.shop.member.dto.request.PointUpdateRequest;
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
        return pointService.pointFindAll(userDto);
    }


    //포인트 등록
    @PostMapping("/points/")
    @ResponseStatus(HttpStatus.CREATED)
    public void pointCreate(@AuthenticationPrincipal UserDto userDto, @RequestBody PointRequest pointRequest){
        pointService.create(userDto, pointRequest);
    }

    //포인트 사용
//    @PostMapping("/points/uses")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void pointUse(@AuthenticationPrincipal UserDto userDto, @RequestBody PointRequest pointRequest){
//        pointService.use(userDto, pointRequest);
//    }

    //포인트 수정
    @PutMapping("/points/{pointId}")
    @ResponseStatus(HttpStatus.OK)
    public void pointUpdate(@AuthenticationPrincipal UserDto userDto, @PathVariable("pointId") long pointId, @RequestBody PointUpdateRequest pointUpdateRequest){
        pointService.update(userDto, pointId, pointUpdateRequest);
    }

    //포인트 삭제
    @DeleteMapping("/points/{pointId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pointDelete(@AuthenticationPrincipal UserDto userDto, @PathVariable("pointId") long pointId){
        pointService.delete(userDto, pointId);
    }

}
