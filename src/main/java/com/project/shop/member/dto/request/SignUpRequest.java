package com.project.shop.member.dto.request;

import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;

public record SignUpRequest(@NotBlank String loginId,
                            @NotBlank String password,
                            @NotBlank String name,
                            @NotBlank String phoneNum,
                            @NotBlank String auth) {

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
