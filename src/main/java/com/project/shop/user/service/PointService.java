package com.project.shop.user.service;

import com.project.shop.user.domain.Point;
import com.project.shop.user.dto.request.PointRequest;
import com.project.shop.user.dto.request.PointUpdateRequest;
import com.project.shop.user.dto.response.PointResponse;
import com.project.shop.user.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    //포인트 전체 조회
    public List<PointResponse> pointFindAll(){

        //로그인된 userId 받기
        long userId = 5;

        //포인트 합계, 소멸 예정 포인트 계산하기

        return pointRepository.findAllByUserId(userId)
                .stream()
                .map(PointResponse::fromEntity)
                .collect(Collectors.toList());

    }

   
    //포인트 등록
    public void create(PointRequest pointRequest){
        pointRepository.save(pointRequest.toEntity());

    }

    //포인트 수정
    public void update(Long pointId, PointUpdateRequest pointUpdateRequest){

        Point point = pointRepository.findById(pointId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_POINT"));

        point.editPoint(pointUpdateRequest);

    }

    //포인트 삭제
    public void delete(long pointId){
        pointRepository.deleteById(pointId);
    }
}
