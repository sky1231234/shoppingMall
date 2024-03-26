package com.project.shop.member.domain;

import com.project.shop.member.dto.request.AddressUpdateRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "address")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long addressId;     //배송지번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;      //고객번호

    @Column(name = "name", nullable = false)
    private String name;    //받는분 이름
    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;    //받는분 전화번호

    @Column(name = "zipcode", nullable = false)
    private String zipcode;    //우편번호
    @Column(name = "address", nullable = false)
    private String address;     //주소
    @Column(name = "addrDetail", nullable = false)
    private String addrDetail;      //상세주소

    @Column(name = "defaultAddr", nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;     //기본배송지 여부

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //배송지 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //배송지 수정일

    @Builder
    public Address(Member member, String name, String phoneNum,
                   String zipcode, String address, String addrDetail,
                   AddressType addressType, LocalDateTime dateTime){
        this.member = member;
        this.name = name;
        this.phoneNum = phoneNum;
        this.zipcode = zipcode;
        this.address = address;
        this.addrDetail = addrDetail;
        this.addressType = addressType;
        this.insertDate = dateTime;
        this.updateDate = dateTime;

    }
    public Address editAddress(AddressUpdateRequest addressUpdateRequest){
        this.name = addressUpdateRequest.receiverName();
        this.zipcode = addressUpdateRequest.zipcode();
        this.address = addressUpdateRequest.address();
        this.addrDetail = addressUpdateRequest.addressDetail();
        this.phoneNum = addressUpdateRequest.receiverPhoneNum();
        this.addressType = addressUpdateRequest.defaultAddr();
        this.updateDate = LocalDateTime.now();
        return this;
    }

}
