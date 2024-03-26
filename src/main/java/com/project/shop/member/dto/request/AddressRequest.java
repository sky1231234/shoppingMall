package com.project.shop.member.dto.request;

import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.AddressType;
import com.project.shop.member.domain.Member;

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

        public Address toEntity(Member member){
                return Address.builder()
                        .member(member)
                        .name(this.receiverName())
                        .zipcode(this.zipcode())
                        .address(this.address())
                        .addrDetail(this.addressDetail())
                        .phoneNum(this.receiverPhoneNum())
                        .addressType(this.defaultAddr())
                        .dateTime(LocalDateTime.now())
                        .build();
        }
}
