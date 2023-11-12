package com.project.shop.user.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
public record SignupRequest(@NotBlank String loginId,
                            @NotBlank String password,
                            @NotBlank String name,
                            @NotBlank String phoneNum) {

}
