package com.project.shop.user.controller;

import com.project.shop.user.dto.request.AddressRequest;
import com.project.shop.user.dto.request.AddressUpdateRequest;
import com.project.shop.user.dto.response.AddressResponse;
import com.project.shop.user.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class AddressController {
    private final AddressService addressService;

    //배송지 전체 조회
    @GetMapping("/address/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressResponse> addressFindAll(@PathVariable("userId") Long userId){
        return addressService.addressFindAll(userId);
    }
 
    //배송지 상세조회
    @GetMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponse addressDetailFind(@PathVariable("addressId") Long addressId){
        return addressService.addressDetailFind(addressId);
    }

    //등록
    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public void addressCreate(@RequestBody AddressRequest addressRequest){
        addressService.create(addressRequest);
    }

    //수정
    @PutMapping("/address/{addressId}}")
    @ResponseStatus(HttpStatus.OK)
    public void addressUpdate(@PathVariable("addressId") Long addressId, @RequestBody AddressUpdateRequest addressUpdateRequest){
        addressService.update(addressId,addressUpdateRequest);
    }

    //삭제
    @DeleteMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public void addressDelete(@PathVariable("addressId") long addressId){
        addressService.delete(addressId);
    }

}
