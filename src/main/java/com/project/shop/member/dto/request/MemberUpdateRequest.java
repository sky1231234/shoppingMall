package com.project.shop.member.dto.request;


import javax.validation.constraints.NotBlank;

public record MemberUpdateRequest(@NotBlank String loginId,
                                  @NotBlank String password,
                                  @NotBlank String name,
                                  @NotBlank String phoneNum) {

}
