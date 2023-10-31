package com.project.shop.user.dto;


import javax.validation.constraints.NotBlank;

public record MyInfoEditRequest(@NotBlank String loginId,
                                @NotBlank String password,
                                @NotBlank String name,
                                @NotBlank String phoneNum) {

}
