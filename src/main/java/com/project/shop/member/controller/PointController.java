package com.project.shop.member.controller;

import com.project.shop.member.dto.request.PointRequest;
import com.project.shop.member.dto.request.PointUpdateRequest;
import com.project.shop.member.dto.response.PointResponse;
import com.project.shop.member.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class PointController {

    private final PointService pointService;

    //포인트 전체 조회
    @GetMapping("/points/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public PointResponse pointFindAll(@PathVariable("userId") long userId){
        return pointService.pointFindAll(userId);
    }


    //포인트 등록
    @PostMapping("/points")
    @ResponseStatus(HttpStatus.CREATED)
    public void pointCreate(long userId, @RequestBody PointRequest pointRequest){
        pointService.create(userId, pointRequest);
    }

    //포인트 수정
    @PutMapping("/points/{pointId}")
    @ResponseStatus(HttpStatus.OK)
    public void pointUpdate(@PathVariable("pointId") long pointId, @RequestBody PointUpdateRequest pointUpdateRequest){
        pointService.update(pointId, pointUpdateRequest);
    }

    //포인트 삭제
    @DeleteMapping("/points/{pointId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pointDelete(@PathVariable("pointId") long pointId){
        pointService.delete(pointId);
    }

//    post로 하나는 포인트적립
//    post로 하나는 포인트 사용
//    포인트 조회
}
