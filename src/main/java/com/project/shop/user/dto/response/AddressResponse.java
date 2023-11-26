package com.project.shop.user.dto.response;

import com.project.shop.user.domain.Address;
import com.project.shop.user.domain.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

    private long addressId;
    private String receiverName;
    private String zipcode;
    private String address;
    private String addressDetail;
    private String receiverPhoneNum;
    private AddressType defaultAddr;

    public static AddressResponse fromEntity(Address address) {

        return AddressResponse.builder()
                .addressId(address.getAddrId())
                .receiverName(address.getName())
                .zipcode(address.getZipcode())
                .address(address.getAddress())
                .addressDetail(address.getAddrDetail())
                .receiverPhoneNum(address.getPhoneNum())
                .defaultAddr(address.getAddressType())
                .build();
    }
}