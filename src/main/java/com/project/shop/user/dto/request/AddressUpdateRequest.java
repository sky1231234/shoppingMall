package com.project.shop.user.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.request.ItemImgUpdateRequest;
import com.project.shop.item.dto.request.OptionUpdateRequest;
import com.project.shop.user.domain.Address;
import com.project.shop.user.domain.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record AddressUpdateRequest(
        @NotBlank String receiverName,
        @NotBlank String zipcode,
        @NotBlank String address,
        @NotBlank String addressDetail,
        @NotBlank String receiverPhoneNum,
        @NotBlank AddressType defaultAddr
    ){

    public Address toEntity(){
        return Address.builder()
                .name(this.receiverName())
                .zipcode(this.zipcode())
                .address(this.address())
                .addrDetail(this.addressDetail())
                .phoneNum(this.receiverPhoneNum())
                .addressType(this.defaultAddr())
                .build();
    }
}
