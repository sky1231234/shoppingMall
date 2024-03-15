package com.project.shop.ordersheet.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "orderSheets")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long orderSheetId;     //주문번호

    @Column(name = "usedPoint", nullable = false)
    private int usedPoint;
    @Column(name = "itemSumPrice", nullable = false)
    private int itemSumPrice;
    @Column(name = "finalPrice", nullable = false)
    private int finalPrice;

    @Column(name = "deliverFee", nullable = false)
    private int deliverFee;     //배송비
    @Column(name = "receiverName", nullable = false)
    private String receiverName;     //받는분 이름
    @Column(name = "zipcode", nullable = false)
    private String zipcode;    //우편번호
    @Column(name = "receiverAddr", nullable = false)
    private String address;     //주소
    @Column(name = "receiverAddrDetail", nullable = false)
    private String addrDetail;      //상세주소
    @Column(name = "receiverPhoneNum", nullable = false)
    private String phoneNum;    //받는분 전화번호

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //주문일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //주문 수정일

    @Builder
    public OrderSheet(int usedPoint, int itemSumPrice, int finalPrice,
                      int deliverFee, String receiverName, String zipcode, String address, String addrDetail, String phoneNum,
                      LocalDateTime dateTime) {
        this.usedPoint = usedPoint;
        this.itemSumPrice = itemSumPrice;
        this.finalPrice = finalPrice;
        this.deliverFee = deliverFee;
        this.receiverName = receiverName;
        this.zipcode = zipcode;
        this.address = address;
        this.addrDetail = addrDetail;
        this.phoneNum =phoneNum;
        this.insertDate = dateTime;
        this.updateDate = dateTime;
    }
}
