package com.project.shop.user.dto.request;


import javax.validation.constraints.NotBlank;

public record UserUpdateRequest(@NotBlank String loginId,
                                @NotBlank String password,
                                @NotBlank String name,
                                @NotBlank String phoneNum) {

}
