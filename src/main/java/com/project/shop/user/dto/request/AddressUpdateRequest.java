package com.project.shop.user.dto.request;

import com.project.shop.user.domain.Address;
import com.project.shop.user.domain.AddressType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
