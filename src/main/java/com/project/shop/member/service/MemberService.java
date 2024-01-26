package com.project.shop.member.service;

import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Cart;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import com.project.shop.member.dto.response.MemberResponse;
import com.project.shop.member.repository.AuthorityRepository;
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
    private final AuthorityRepository authorityRepository;
    private final CartRepository cartRepository;

    //회원 정보 전체 조회
    public List<MemberResponse> userFindAll(String loginId){

        Member member = findLoginMember(loginId);

        Authority authority = authorityRepository.findByMember(member);

        if(authority.getAuthName().equals("user"))
            throw new RuntimeException("ONLY_ADMIN");

        return memberRepository.findAll()
                .stream()
                .map(x -> {
                    Authority auth = authorityRepository.findByMember(x);
                    return MemberResponse.fromEntity(x,auth);
                })
                .toList();

    }

    //내 정보 조회
    public MemberResponse userDetailFind(String loginId){

        Member member = findLoginMember(loginId);

        Authority authority = authorityRepository.findByMember(member);
        return MemberResponse.fromEntity(member,authority);
    }


    //회원 정보 수정
    public void update(String loginId, MemberUpdateRequest memberUpdateRequest){

        Member member = findLoginMember(loginId);
        Member updateMember = member.updateUser(memberUpdateRequest);
        memberRepository.save(updateMember);

        Authority authority = authorityRepository.findByMember(member);
        Authority updateAuth = authority.updateAuth(memberUpdateRequest.auth());
        authorityRepository.save(updateAuth);
    }

    //회원 탈퇴
    public void delete(String loginId){

        Member member = findLoginMember(loginId);

        //권한 삭제
        Authority authority = authorityRepository.findByMember(member);
        authorityRepository.delete(authority);

        //member soft delete
        memberRepository.delete(member);

        //장바구니 삭제
        List<Cart> cart = cartRepository.findAllByMember(member);
        if (!cart.isEmpty())
            cartRepository.deleteAll(cart);

    }

    //로그인 member 확인
    private Member findLoginMember(String loginId){

        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
    }



}
