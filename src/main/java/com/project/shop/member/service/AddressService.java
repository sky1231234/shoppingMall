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


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    //주소 전체 조회
    public List<AddressResponse> findAll(String loginId){

        Member member = findLoginMember(loginId);

        return addressRepository.findAllByMember(member)
                .stream()
                .map(AddressResponse::fromEntity)
                .collect(Collectors.toList());

    }

    //주소 상세 조회
    public AddressResponse detailFind(String loginId, long addressId){

        Member member = findLoginMember(loginId);
        Address address = orderFindById(addressId);
        equalLoginMemberCheck(member,address);

        return AddressResponse.fromEntity(address);
    }

    //주소 등록
    @Transactional
    public long create(String loginId, AddressRequest addressRequest){

        Member member = findLoginMember(loginId);

        var address = addressRepository.save(addressRequest.toEntity(member));

        return address.getAddressId();
    }

    //주소 수정
    @Transactional
    public void update(String loginId, long addressId, AddressUpdateRequest addressUpdateRequest){

        Member member = findLoginMember(loginId);
        Address address = orderFindById(addressId);
        equalLoginMemberCheck(member,address);

        addressRepository.save(address.editAddress(addressUpdateRequest));
    }

    //주소 삭제
    @Transactional
    public void delete(String loginId, long addressId){

        Member member = findLoginMember(loginId);
        Address address = orderFindById(addressId);
        equalLoginMemberCheck(member,address);

        addressRepository.deleteById(addressId);
    }

    //로그인 member 확인
    private Member findLoginMember(String loginId){

        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
    }

    //로그인 member와 address member 비교
    private void equalLoginMemberCheck(Member member, Address address){
        if( ! member.equals(address.getMember()) )
            throw new RuntimeException("NOT_EQUAL_ADDRESS_MEMBER");
    }

    //address 확인
    private Address orderFindById(long addressId){

        return addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ADDRESS"));
    }

}
