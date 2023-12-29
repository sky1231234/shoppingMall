package com.project.shop.member.dto.request;

import com.project.shop.member.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record SignUpRequest(@NotBlank String loginId,
                            @NotBlank String password,
                            @NotBlank String name,
                            @NotBlank String phoneNum) {

    public Member toEntity(PasswordEncoder passwordEncoder){
        return Member.builder()
                .loginId(this.loginId())
                .password(passwordEncoder.encode(this.password()))
                .name(this.name())
                .phoneNum(this.phoneNum())
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

}
