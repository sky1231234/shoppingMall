package com.project.shop.member.service;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.domain.Cart;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import com.project.shop.member.dto.response.MemberResponse;
import com.project.shop.member.repository.CartRepository;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;



    //내 정보 조회
    public MemberResponse userDetailFind(UserDto userDto){

        Member member = findLoginMember(userDto);

        return MemberResponse.fromEntity(member);
    }


    //회원 정보 수정
    public void update(UserDto userDto, MemberUpdateRequest memberUpdateRequest){

        Member member = findLoginMember(userDto);

        Member update = member.updateUser(memberUpdateRequest);
        memberRepository.save(update);
    }

    //회원 탈퇴
    public void delete(UserDto userDto){

        Member member = findLoginMember(userDto);

        Member delete = member.deleteUser();
        memberRepository.save(delete);

        //장바구니 삭제
        List<Cart> cart = cartRepository.findAllByMember(member);
        cartRepository.deleteAll(cart);

    }

    //로그인 member 확인
    private Member findLoginMember(UserDto userDto){

        return memberRepository.findByLoginId(userDto.getLoginId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
    }



}
