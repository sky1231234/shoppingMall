package com.project.shop.member.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.request.AddressRequest;
import com.project.shop.member.dto.request.AddressUpdateRequest;
import com.project.shop.member.dto.response.AddressResponse;
import com.project.shop.member.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<AddressResponse>> findAll(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(addressService.findAll(userDto.getLoginId()));
    }
 
    //배송지 상세 조회
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> detailFind(@AuthenticationPrincipal UserDto userDto, @PathVariable("addressId") Long addressId){
        return ResponseEntity.ok()
                .body(addressService.detailFind(userDto.getLoginId(), addressId));
    }

    //등록
    @PostMapping
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal UserDto userDto,
                                  @RequestBody AddressRequest addressRequest){
        long addressId = addressService.create(userDto.getLoginId(), addressRequest);
        return ResponseEntity.created(URI.create("/members/addresses"+addressId)).build();
    }

    //수정
    @PutMapping("/{addressId}")
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto,
                              @PathVariable("addressId") Long addressId, @RequestBody AddressUpdateRequest addressUpdateRequest){
        addressService.update(userDto.getLoginId(), addressId,addressUpdateRequest);
        return ResponseEntity.ok().build();
    }

    //삭제
    @DeleteMapping("/{addressId}")
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal UserDto userDto,
                              @PathVariable("addressId") long addressId){
        addressService.delete(userDto.getLoginId(), addressId);
        return ResponseEntity.noContent().build();
    }

}
