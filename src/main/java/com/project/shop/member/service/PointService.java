package com.project.shop.member.service;

import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.Member;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.dto.request.PointRequest;
import com.project.shop.member.dto.request.PointUpdateRequest;
import com.project.shop.member.dto.request.PointUseRequest;
import com.project.shop.member.dto.response.PointResponse;
import com.project.shop.member.repository.AuthorityRepository;
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
    private final AuthorityRepository authorityRepository;

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
    public void create(String loginId, PointRequest pointRequest){

        authCheck(loginId);

        Member member = memberRepository.findById(pointRequest.id())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER_FOR_POINT"));

        pointRepository.save(pointRequest.toEntity(member, PointType.적립));

    }

    //포인트 사용
    public void use(String loginId, PointUseRequest pointUseRequest){

        authCheck(loginId);

        Member member = memberRepository.findById(pointUseRequest.id())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER_FOR_POINT"));

        var sumPoint = pointRepository.findSumPoint(member.getUserId());
        if ( sumPoint > pointUseRequest.point())
            pointRepository.save(pointUseRequest.toEntity(member,PointType.사용));
        else
            throw new RuntimeException("NOT_USE_ENOUGH_POINT");

    }

    //로그인 member 확인
    private Member findLoginMember(String loginId){

        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
    }

    //admin 권한 확인
    private void authCheck(String loginId){

        Member member = findLoginMember(loginId);
        Authority authority = authorityRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_AUTH"));;

        if(authority.getAuthName().equals("user"))
            throw new RuntimeException("ONLY_ADMIN");
    }
}
