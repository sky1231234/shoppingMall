package com.project.shop.member.builder;

import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.AddressType;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.AddressRequest;
import com.project.shop.member.dto.request.AddressUpdateRequest;


import java.time.LocalDateTime;

public class AddressBuilder {

    public static Address createAddress1(Member member){

        return Address.builder()
                .member(member)
                .name("이름")
                .zipcode("4014040")
                .address("서울시")
                .addrDetail("상세등록주소")
                .phoneNum("010100200")
                .addressType(AddressType.Y)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static Address createAddress2(Member member){

        return Address.builder()
                .member(member)
                .name("부산사람")
                .zipcode("200000")
                .address("부산")
                .addrDetail("해운대")
                .phoneNum("11111111")
                .addressType(AddressType.N)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static AddressRequest createAddressRequest(){

        return new AddressRequest(
                "받는사람",
                "501010",
                "서울",
                "서초구",
                "01000000000",
                AddressType.Y
                );
    }

    public static AddressUpdateRequest createAddressUpdateRequest(){

        return new AddressUpdateRequest(
                "받는사람",
                "501010",
                "서울",
                "서초구",
                "01000000000",
                AddressType.N
        );
    }

}
