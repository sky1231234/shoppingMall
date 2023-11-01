package com.project.shop.item.dto;

import javax.validation.constraints.NotBlank;

public record ItemRequest(@NotBlank String loginId, @NotBlank String password) {
}
