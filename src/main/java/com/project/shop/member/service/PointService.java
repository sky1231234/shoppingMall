package com.project.shop.member.service;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.domain.Address;
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
    public PointResponse pointFindAll(UserDto userDto){
        Member member = findLoginMember(userDto);

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
    public void create(UserDto userDto, PointRequest pointRequest){

        Member member = findLoginMember(userDto);

        pointRepository.save(pointRequest.toEntity(member));

    }

    //포인트 사용
    //수정하기
    public void use(UserDto userDto, PointRequest pointRequest){

        Member member = findLoginMember(userDto);

        pointRepository.save(pointRequest.toEntity(member));

    }

    //포인트 수정
    public void update(UserDto userDto, long pointId, PointUpdateRequest pointUpdateRequest){

        Member member = findLoginMember(userDto);
        Point point = pointFindById(pointId);
        equalLoginMemberCheck(member, point);

        Point update = point.editPoint(pointUpdateRequest);
        pointRepository.save(update);

    }

    //포인트 삭제
    public void delete(UserDto userDto, long pointId){
        Member member = findLoginMember(userDto);
        Point point = pointFindById(pointId);
        equalLoginMemberCheck(member, point);
        pointRepository.deleteById(pointId);
    }

    //로그인 member 확인
    private Member findLoginMember(UserDto userDto){

        return memberRepository.findByLoginId(userDto.getLoginId())
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
