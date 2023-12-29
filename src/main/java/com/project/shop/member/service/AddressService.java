package com.project.shop.member.service;

import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.AddressRequest;
import com.project.shop.member.dto.request.AddressUpdateRequest;
import com.project.shop.member.dto.response.AddressResponse;
import com.project.shop.member.repository.AddressRepository;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//import static com.project.shop.global.exception.ErrorCode.NOT_FOUND_ITEM;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    //주소 전체 조회
    public List<AddressResponse> addressFindAll(long userId){

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        return addressRepository.findAllByUsers(member)
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
