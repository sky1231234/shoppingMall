package com.project.shop.member.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.item.service.ItemService;
import com.project.shop.member.builder.AddressBuilder;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.AddressType;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.dto.request.AddressRequest;
import com.project.shop.member.dto.request.AddressUpdateRequest;
import com.project.shop.member.dto.response.AddressResponse;
import com.project.shop.member.repository.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

public class AddressServiceTest extends ServiceCommon {

    @Autowired
    AddressService addressService;
    @Autowired
    AddressRepository addressRepository;
    Address address;

    @BeforeEach
    public void before(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        member2 = memberBuilder.signUpAdminMember();
        var memberSave = memberRepository.save(member1);
        var adminSave = memberRepository.save(member2);

        //auth
        Authority auth = memberBuilder.auth(memberSave);
        Authority authAdmin = memberBuilder.authAdmin(adminSave);
        authorityRepository.save(auth);
        authorityRepository.save(authAdmin);

        //address
        address = AddressBuilder.createAddress1(member1);
        addressRepository.save(address);
    }

    @Test
    @DisplayName("주소 전체 조회")
    void addressFindAllTest(){
        //given
        Address address1 = AddressBuilder.createAddress2(member1);
        addressRepository.save(address1);

        //when
        List<AddressResponse> addressResponses = addressService.findAll(member1.getLoginId());

        //then
        Assertions.assertThat(addressResponses.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("주소 상세 조회")
    void addressFindDetailTest(){
        //given
        Address address1 = AddressBuilder.createAddress2(member1);
        addressRepository.save(address1);
        long addressId= 2 ;
        //when
        AddressResponse addressResponse = addressService.detailFind(member1.getLoginId(),addressId);

        //then
        Assertions.assertThat(addressResponse.getReceiverName()).isEqualTo("부산사람");
    }



    @Test
    @DisplayName("주소 등록")
    void addressCreateTest(){

        //given
        AddressRequest addressRequest = AddressBuilder.createAddressRequest();

        //when
        var addressId = addressService.create(member1.getLoginId(), addressRequest);

        //then
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ADDRESS_TEST"));

        Assertions.assertThat(address.getAddress()).isEqualTo("서울");

    }

    @Test
    @DisplayName("주소 수정")
    void addressUpdateTest(){
        //given
        long addressId = 1;
        AddressUpdateRequest addressUpdateRequest = AddressBuilder.createAddressUpdateRequest();

        //when
        addressService.update(member1.getLoginId(), addressId, addressUpdateRequest);

        //then
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ADDRESS_TEST"));

        Assertions.assertThat(address.getAddrDetail()).isEqualTo("서초구");
        Assertions.assertThat(address.getAddressType()).isEqualTo(AddressType.N);

    }

    @Test
    @DisplayName("주소 삭제")
    void addressDeleteTest(){
        //given
        long addressId = 1;

        //when
        addressService.delete(member1.getLoginId(), addressId);

        //then
        Assertions.assertThat(addressRepository.findAll().size()).isEqualTo(0);


    }

}
