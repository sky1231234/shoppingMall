package com.project.shop.order.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "order")
@Entity
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int orderId;     //주문번호
    @Column(name = "userId", nullable = false)
    private int userId;     //고객 번호
    @Column(name = "deliverFee", nullable = false)
    private int deliverFee;     //배송비
    @Column(name = "price", nullable = false)
    private int price;     //가격
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;    //주문상태
    @Column(name = "receiverName")
    private String receiverName;     //받는분 이름
    @Column(name = "zipcode", nullable = false)
    private String zipcode;    //우편번호
    @Column(name = "receiverAddr", nullable = false)
    private String address;     //주소
    @Column(name = "receiverAddrDetail", nullable = false)
    private String addrDetail;      //상세주소
    @Column(name = "receiverPhoneNum", nullable = false)
    private String phoneNum;    //받는분 전화번호
    @Column(name = "receiverMsg")
    private String msg;     //배송메시지

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //주문일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //주문 수정일

}
