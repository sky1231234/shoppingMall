package com.project.shop.global.config.security.service;

import javax.transaction.Transactional;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.MemberRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Component("userDetailsService")
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException(loginId + " : NOT_FOUND_LOGIN_ID"));

        if(member != null){
            return new UserDto(member.getLoginId(),member.getPassword(),member.getName(), member.getUserRole());
        }
        return null;
    }

}
