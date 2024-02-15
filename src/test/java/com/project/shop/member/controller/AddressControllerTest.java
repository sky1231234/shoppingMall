package com.project.shop.member.controller;

import com.project.shop.common.controller.ControllerCommon;
import com.project.shop.member.builder.AddressBuilder;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.dto.request.AddressRequest;
import com.project.shop.member.repository.AddressRepository;
import com.project.shop.mock.WithCustomMockUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AddressControllerTest extends ControllerCommon {

    @Autowired
    AddressRepository addressRepository;
    Address address;

    @BeforeEach
    void beforeEach(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        var memberSave = memberRepository.save(member1);

        Authority auth = memberBuilder.auth(memberSave);
        authorityRepository.save(auth);

        //address
        address = AddressBuilder.createAddress1(member1);
        addressRepository.save(address);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("배송지 전체 조회")
    void userDetailFind() throws Exception {
        //given
        Address address1 = AddressBuilder.createAddress2(member1);
        addressRepository.save(address1);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(2)));

    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("배송지 상세 조회")
    void addressDetailFind() throws Exception {

        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/addresses",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("서울시"))
                .andExpect(jsonPath("$[0].defaultAddr").value("Y"));

    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주소 등록")
    void addressCreate() throws Exception {
        //given
        AddressRequest addressRequest = AddressBuilder.createAddressRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/members/addresses")
                .content(objectMapper.writeValueAsString(addressRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        //then
        Assertions.assertThat(addressRepository.findById(2L).get().getAddrDetail()).isEqualTo("서초구");
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주소 수정")
    void addressUpdate() throws Exception {
        //given
        AddressRequest addressRequest = AddressBuilder.createAddressRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/members/addresses/{addrId}",1)
                        .content(objectMapper.writeValueAsString(addressRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(addressRepository.findById(1L).get().getZipcode()).isEqualTo("501010");
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("주소 삭제")
    void addressDelete() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/members/addresses/{addrId}",1))
                .andExpect(status().isNoContent());

        //then
        Assertions.assertThat(addressRepository.findAll().size()).isEqualTo(0);
    }

}
