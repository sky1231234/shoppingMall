package com.project.shop.item.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record OptionEnrollRequest(
        @NotBlank String size,
        @NotBlank String color
        ) {
}
