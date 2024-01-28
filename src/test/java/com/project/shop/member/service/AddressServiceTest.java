package com.project.shop.member.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.item.service.ItemService;
import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.AddressType;
import com.project.shop.member.dto.request.AddressRequest;
import com.project.shop.member.repository.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressServiceTest extends ServiceCommon {

    @Autowired
    AddressService addressService;

    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    public void before(){

    }

    @Test
    @DisplayName("주소 전체 조회")
    void addressFindAllTest(){

    }

    @Test
    @DisplayName("주소 상세 조회")
    void addressFindDetailTest(){

    }

//    @Test
//    @DisplayName("주소 등록")
//    void addressCreateTest(){
//        AddressRequest addressRequest = new AddressRequest(
//                "받는사람",
//                "01001",
//                "서울",
//                "서초구",
//                "010101010",
//                AddressType.Y);
//
//        var addressId = addressService.create(addressRequest);
//
//        Address address = addressRepository.findById(addressId)
//                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ADDRESS_TEST"));
//
//        Assertions.assertThat(address.getAddress()).isEqualTo("서울");
//
//    }

    @Test
    @DisplayName("주소 수정")
    void addressUpdateTest(){

    }

    @Test
    @DisplayName("주소 삭제")
    void addressDeleteTest(){

    }

    void create(){

    }

}
