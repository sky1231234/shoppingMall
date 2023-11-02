package com.project.shop.item.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record ItemRequest(
        @NotBlank String categoryName,
        @NotBlank String brandName,
        @NotBlank String itemName,
        @NotBlank String price,
        @NotBlank String explain,
        @NotBlank List<ItemImgEnrollRequest> itemImgEnrollRequestList,
        @NotBlank List<OptionEnrollRequest> optionEnrollRequestList
        ) {
}
