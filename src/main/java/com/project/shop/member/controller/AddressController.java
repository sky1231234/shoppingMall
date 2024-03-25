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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/addresses")
@RequiredArgsConstructor
@Validated
@Tag( name = "AddressController", description = "[사용자] 배송지 API")
public class AddressController {
    private final AddressService addressService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<AddressResponse>> findAll(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(addressService.findAll(userDto.getLoginId()));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> detailFind(@AuthenticationPrincipal UserDto userDto, @PathVariable("addressId") Long addressId){
        return ResponseEntity.ok()
                .body(addressService.detailFind(userDto.getLoginId(), addressId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal UserDto userDto,
                                  @RequestBody AddressRequest addressRequest){
        long addressId = addressService.create(userDto.getLoginId(), addressRequest);
        return ResponseEntity.created(URI.create("/members/addresses"+addressId)).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{addressId}")
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto,
                              @PathVariable("addressId") Long addressId, @RequestBody AddressUpdateRequest addressUpdateRequest){
        addressService.update(userDto.getLoginId(), addressId,addressUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal UserDto userDto,
                              @PathVariable("addressId") long addressId){
        addressService.delete(userDto.getLoginId(), addressId);
        return ResponseEntity.noContent().build();
    }

}
