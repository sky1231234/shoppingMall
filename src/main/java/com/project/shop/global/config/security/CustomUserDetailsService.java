package com.project.shop.global.config.security;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

//@Service
@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        return memberRepository.findOneWithAuthoritiesByLoginId(loginId)
                .map(member -> createUser(loginId, member))
                .orElseThrow(() -> new UsernameNotFoundException(loginId + " : NOT_FOUND_LOGIN_ID"));
    }


    /** security.core.userdetails.User 정보를 생성한다. */
    private User createUser(String loginId, Member member) {
//        if(!member.isAc)

        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                member.getLoginId(),
                member.getPassword(),
                grantedAuthorities);
    }

}
