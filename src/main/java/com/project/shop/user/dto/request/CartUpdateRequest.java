package com.project.shop.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record CartUpdateRequest(
        @NotBlank int count,
        @NotBlank long optionNum
        ) {
}
