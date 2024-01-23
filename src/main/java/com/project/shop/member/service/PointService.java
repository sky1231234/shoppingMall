package com.project.shop.member.service;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.Member;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.dto.request.PointRequest;
import com.project.shop.member.dto.request.PointUpdateRequest;
import com.project.shop.member.dto.request.PointUseRequest;
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
    public PointResponse pointFindAll(String loginId){
        Member member = findLoginMember(loginId);

        var sumPoint = pointRepository.findSumPoint(member.getUserId());
        var disappearPoint = pointRepository.findDisappearPoint(member.getUserId());

        List<Point> pointList = pointRepository.findAllByMember(member);

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
    public void create(PointRequest pointRequest){

        Member member = memberRepository.findById(pointRequest.id())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER_FOR_POINT"));

        pointRepository.save(pointRequest.toEntity(member, PointType.적립));

    }

    //포인트 사용
    public void use(PointUseRequest pointUseRequest){

        Member member = memberRepository.findById(pointUseRequest.id())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER_FOR_POINT"));

        var sumPoint = pointRepository.findSumPoint(member.getUserId());
        if ( sumPoint > pointUseRequest.point())
            pointRepository.save(pointUseRequest.toEntity(member,PointType.사용));
        else
            throw new RuntimeException("NOT_USE_ENOUGH_POINT");

    }

    //포인트 수정
    public void update(long pointId, PointUpdateRequest pointUpdateRequest){

        Point point = pointFindById(pointId);

        Point update = point.editPoint(pointUpdateRequest);
        pointRepository.save(update);

    }

    //포인트 삭제
    public void delete(long pointId){

        Point point = pointFindById(pointId);
        pointRepository.deleteById(pointId);
    }

    //로그인 member 확인
    private Member findLoginMember(String loginId){

        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
    }

    //point 확인
    private Point pointFindById(long pointId){

        return pointRepository.findById(pointId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_POINT"));
    }

    //로그인 member와 point member 비교
    private void equalLoginMemberCheck(Member member, Point point){
        if( ! member.equals(point.getMember()) )
            throw new RuntimeException("NOT_EQUAL_POINT_MEMBER");
    }
}
