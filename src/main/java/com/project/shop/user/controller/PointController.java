package com.project.shop.user.controller;

import com.project.shop.user.dto.request.PointRequest;
import com.project.shop.user.dto.request.PointUpdateRequest;
import com.project.shop.user.dto.response.PointResponse;
import com.project.shop.user.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class PointController {

    private final PointService pointService;

    //포인트 전체 조회
    @GetMapping("/point")
    @ResponseStatus(HttpStatus.OK)
    public List<PointResponse> pointFindAll(){
        return pointService.pointFindAll();
    }
    

    //포인트 등록
    @PostMapping("/point")
    @ResponseStatus(HttpStatus.CREATED)
    public void pointCreate(@RequestBody PointRequest pointRequest){
        pointService.create(pointRequest);
    }

    //포인트 수정
    @PutMapping("/point/{pointId}}")
    @ResponseStatus(HttpStatus.OK)
    public void pointUpdate(@PathVariable("pointId") long pointId, @RequestBody PointUpdateRequest pointUpdateRequest){
        pointService.update(pointId, pointUpdateRequest);
    }

    //포인트 삭제
    @DeleteMapping("/point/{pointId}")
    @ResponseStatus(HttpStatus.OK)
    public void pointDelete(@PathVariable("pointId") long pointId){
        pointService.delete(pointId);
    }

}
