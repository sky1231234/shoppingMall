package com.project.shop.member.dto.response;

import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

//    {
//        "addrId" : 1,
//        "receiverName" : "받는사람이름",
//        "zipcode" : "우편번호",
//        "address" : "주소",
//        "addressDetail" : "상세주소",
//        "receiverPhoneNum" : "받는사람전화번호",
//        "defaultAddr" : "기본배송지 여부",
//    }

    private long addressId;
    private String receiverName;
    private String zipcode;
    private String address;
    private String addressDetail;
    private String receiverPhoneNum;
    private AddressType defaultAddr;

    public static AddressResponse fromEntity(Address address) {

        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .receiverName(address.getName())
                .zipcode(address.getZipcode())
                .address(address.getAddress())
                .addressDetail(address.getAddrDetail())
                .receiverPhoneNum(address.getPhoneNum())
                .defaultAddr(address.getAddressType())
                .build();
    }
}