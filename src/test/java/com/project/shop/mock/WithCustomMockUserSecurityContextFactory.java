package com.project.shop.mock;

import com.project.shop.global.config.security.domain.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements
        WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String loginId = annotation.loginId();
        String authority = annotation.authority();

        UserDto user = new UserDto(loginId,"auth","",List.of(new SimpleGrantedAuthority(authority)));

        Authentication auth = new UsernamePasswordAuthenticationToken(user, "",
                List.of(new SimpleGrantedAuthority(authority)));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);

        return context;
    }
}