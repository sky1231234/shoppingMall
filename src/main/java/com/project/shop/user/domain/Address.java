package com.project.shop.user.domain;

import com.project.shop.user.dto.request.AddressUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@Getter
@Builder
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addrId")
    private long addrId;     //배송지번호

    @Column(name = "userId", nullable = false)
    private long userId;      //고객번호
    @Column(name = "name", nullable = false)
    private String name;    //받는분 이름
    @Column(name = "zipcode", nullable = false)
    private String zipcode;    //우편번호
    @Column(name = "address", nullable = false)
    private String address;     //주소
    @Column(name = "addrDetail", nullable = false)
    private String addrDetail;      //상세주소
    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;    //받는분 전화번호
    @Column(name = "defaultAddr", nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;     //기본배송지 여부

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //배송지 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //배송지 수정일

    public Address editAddress(AddressUpdateRequest addressUpdateRequest){
        this.name = addressUpdateRequest.getReceiverName();
        this.zipcode = addressUpdateRequest.getZipcode();
        this.address = addressUpdateRequest.getAddress();
        this.addrDetail = addressUpdateRequest.getAddressDetail();
        this.phoneNum = addressUpdateRequest.getReceiverPhoneNum();
        this.addressType = addressUpdateRequest.getDefaultAddr();
        return this;
    }

}
