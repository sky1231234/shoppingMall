package com.project.shop.item.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record CategoryUpdateRequest(
        @NotBlank String auth,
        @NotBlank String categoryName,
        @NotBlank String brandName
        ) {
}
