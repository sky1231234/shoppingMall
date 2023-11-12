package com.project.shop.user.dto.request;


import javax.validation.constraints.NotBlank;

public record UserRequest(@NotBlank String loginId,
                          @NotBlank String password,
                          @NotBlank String name,
                          @NotBlank String phoneNum) {

}
