package com.project.shop.global.config.security.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserDto extends User {

    private final String loginId;
    private final String username;

    public UserDto(String loginId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.loginId = loginId;
        this.username = username;
    }
}
