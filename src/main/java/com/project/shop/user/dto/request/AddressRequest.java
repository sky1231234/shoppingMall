package com.project.shop.user.dto.request;

import com.project.shop.user.domain.Address;
import com.project.shop.user.domain.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
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
                        .name(this.getReceiverName())
                        .zipcode(this.getZipcode())
                        .address(this.getAddress())
                        .addrDetail(this.getAddressDetail())
                        .phoneNum(this.getReceiverPhoneNum())
                        .addressType(this.getDefaultAddr())
                        .build();
        }
}
