package com.project.shop.order.domain;

import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "orders")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long orderId;     //주문번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;     //고객 번호

    @Column(name = "orderNum", nullable = false)
    private String orderNum;     //주문비즈니스키
    @Column(name = "deliverFee", nullable = false)
    private int deliverFee;     //배송비
    @Column(name = "point", nullable = false)
    private int point;     //포인트
    @Column(name = "price", nullable = false)
    private int price;     //가격
    @Column(name = "orderState", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;    //주문상태
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
    @Column(name = "receiverMsg")
    private String msg;     //배송메시지

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //주문일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //주문 수정일

    public Order updateOrder(OrderUpdateRequest orderUpdateRequest){
        this.price = orderUpdateRequest.orderTotalPrice();
        this.deliverFee = orderUpdateRequest.deliverFee();
        this.receiverName = orderUpdateRequest.receiverName();
        this.zipcode = orderUpdateRequest.zipcode();
        this.address = orderUpdateRequest.address();
        this.addrDetail = orderUpdateRequest.addressDetail();
        this.phoneNum = orderUpdateRequest.receiverPhoneNum();
        this.msg = orderUpdateRequest.addrMsg();
        this.updateDate = LocalDateTime.now();
        return this;
    }

    public Order cancelOrder(OrderType orderType){
        this.orderType =orderType;
        this.updateDate = LocalDateTime.now();
        return this;
    }

}
