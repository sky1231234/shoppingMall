package com.project.shop.user.service;

import com.project.shop.user.domain.Address;
import com.project.shop.user.dto.request.AddressRequest;
import com.project.shop.user.dto.request.AddressUpdateRequest;
import com.project.shop.user.dto.response.AddressResponse;
import com.project.shop.user.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import static com.project.shop.global.exception.ErrorCode.NOT_FOUND_ITEM;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    //주소 전체 조회
    public List<AddressResponse> addressFindAll(long userId){

        return addressRepository.findAllByUserId(userId)
                .stream()
                .map(AddressResponse::fromEntity)
                .collect(Collectors.toList());

    }

    //주소 상세 조회
    public AddressResponse addressDetailFind(long addressId){

        Address address = addressRepository.findById(addressId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_ADDRESS"));

        return AddressResponse.fromEntity(address);
    }

    //주소 등록
    public void create(AddressRequest addressRequest){

        addressRepository.save(addressRequest.toEntity());

    }

    //주소 수정
    public void update(Long addressId, AddressUpdateRequest addressUpdateRequest){

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ADDRESS"));

        addressRepository.save(address.editAddress(addressUpdateRequest));
    }

    //주소 삭제
    public void delete(long addressId){
        addressRepository.deleteById(addressId);
    }
}
