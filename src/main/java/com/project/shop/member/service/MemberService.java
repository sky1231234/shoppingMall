package com.project.shop.member.service;

import com.project.shop.global.config.security.Authority;
import com.project.shop.global.config.security.JwtFilter;
import com.project.shop.global.config.security.TokenResponse;
import com.project.shop.global.config.security.TokenProvider;
import com.project.shop.member.domain.Cart;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.LoginRequest;
import com.project.shop.member.dto.request.SignUpRequest;
import com.project.shop.member.dto.request.MemberUpdateRequest;
import com.project.shop.member.dto.response.MemberResponse;
import com.project.shop.member.repository.CartRepository;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public MemberResponse userDetailFind(long userId){
        Member member = memberRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_USER"));

        return MemberResponse.fromEntity(member);
    }


    //회원 정보 수정
    public void update(long userId, MemberUpdateRequest memberUpdateRequest){

        Member member = memberRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_USER"));

        Member update = member.updateUser(memberUpdateRequest);
        memberRepository.save(update);
    }

    //회원 탈퇴
    public void delete(long userId){

        Member member = memberRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_USER"));

        Member delete = member.deleteUser();
        memberRepository.save(delete);

        //장바구니 삭제
        List<Cart> cart = cartRepository.findByUsers(member);
        cartRepository.deleteAll(cart);


    }

}
