package com.project.shop.user.dto.request;

import com.project.shop.user.domain.Address;
import com.project.shop.user.domain.AddressType;

import javax.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank String receiverName,
        @NotBlank String zipcode,
        @NotBlank String address,
        @NotBlank String addressDetail,
        @NotBlank String receiverPhoneNum,
        @NotBlank AddressType defaultAddr

        ) {

        public Address toEntity(){
                return Address.builder()
                        .name(this.receiverName())
                        .zipcode(this.zipcode())
                        .address(this.address())
                        .addrDetail(this.addressDetail())
                        .phoneNum(this.receiverPhoneNum())
//                        .addressType(this.defaultAddr())
                        .build();
        }
}
