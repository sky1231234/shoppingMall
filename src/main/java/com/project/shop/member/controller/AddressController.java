package com.project.shop.member.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.request.AddressRequest;
import com.project.shop.member.dto.request.AddressUpdateRequest;
import com.project.shop.member.dto.response.AddressResponse;
import com.project.shop.member.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/members/addresses")
@RequiredArgsConstructor
@Validated
@Tag( name = "AddressController", description = "[사용자] 배송지 API")
public class AddressController {
    private final AddressService addressService;

    //배송지 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AddressResponse> findAll(@AuthenticationPrincipal UserDto userDto){
        return addressService.findAll(userDto.getLoginId());
    }
 
    //배송지 상세 조회
    @GetMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponse detailFind(@AuthenticationPrincipal UserDto userDto, @PathVariable("addressId") Long addressId){
        return addressService.detailFind(userDto.getLoginId(), addressId);
    }

    //등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@AuthenticationPrincipal UserDto userDto,
                                  @RequestBody AddressRequest addressRequest){
        addressService.create(userDto.getLoginId(), addressRequest);
    }

    //수정
    @PutMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@AuthenticationPrincipal UserDto userDto,
                              @PathVariable("addressId") Long addressId, @RequestBody AddressUpdateRequest addressUpdateRequest){
        addressService.update(userDto.getLoginId(), addressId,addressUpdateRequest);
    }

    //삭제
    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal UserDto userDto,
                              @PathVariable("addressId") long addressId){
        addressService.delete(userDto.getLoginId(), addressId);
    }

}
