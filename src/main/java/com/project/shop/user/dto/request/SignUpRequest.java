package com.project.shop.user.dto.request;

import com.project.shop.user.domain.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record SignUpRequest(@NotBlank String loginId,
                            @NotBlank String password,
                            @NotBlank String name,
                            @NotBlank String phoneNum) {

    public User toEntity(){
        return User.builder()
                .loginId(this.loginId())
                .password(this.password())
                .name(this.name())
                .phoneNum(this.phoneNum())
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

}
