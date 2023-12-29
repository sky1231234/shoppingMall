package com.project.shop.member.service;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.PointRequest;
import com.project.shop.member.dto.request.PointUpdateRequest;
import com.project.shop.member.dto.response.PointResponse;
import com.project.shop.member.repository.PointRepository;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    //포인트 전체 조회
    public PointResponse pointFindAll(long userId){

        var sumPoint = pointRepository.findSumPoint(userId);
        var disappearPoint = pointRepository.findDisappearPoint(userId);

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        List<Point> pointList = pointRepository.findAllByUsers(member);

        var list =  pointList.stream().map( x -> {
            return PointResponse.PointList.builder()
                    .pointId(x.getPointId())
                    .point(x.getPoint())
                    .deadlineDate(x.getDeadlineDate())
                    .state(x.getPointType())
                    .date(x.getInsertDate())
                    .build();
                })
                .toList();

        return PointResponse.builder()
                .sumPoint(sumPoint)
                .disappearPoint(disappearPoint)
                .pointList(list)
                .build();
    }

   
    //포인트 등록
    public void create(long userId, PointRequest pointRequest){

        //userId 받아오기
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        pointRepository.save(pointRequest.toEntity(member));

    }

    //포인트 수정
    public void update(Long pointId, PointUpdateRequest pointUpdateRequest){

        Point point = pointRepository.findById(pointId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_POINT"));

        Point update = point.editPoint(pointUpdateRequest);
        pointRepository.save(update);

    }

    //포인트 삭제
    public void delete(long pointId){
        pointRepository.deleteById(pointId);
    }
}
