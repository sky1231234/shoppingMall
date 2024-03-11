package com.project.shop.item.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;


public record ItemUpdateRequest(
        @NotBlank CategoryUpdateRequest categoryUpdateRequest,
        @NotBlank String itemName,
        @NotBlank int price,
        @NotBlank String explain,

        @NotBlank List<ImgUpdateRequest> itemImgUpdateRequestList,
        @NotBlank List<OptionUpdateRequest> optionUpdateRequestList) {

}
