package com.project.shop.member.dto.request;

import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.AddressType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
                        .addressType(this.defaultAddr())
                        .insertDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();
        }
}
