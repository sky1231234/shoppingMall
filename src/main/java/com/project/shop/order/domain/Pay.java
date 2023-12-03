package com.project.shop.order.domain;

import com.project.shop.order.dto.request.OrderUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "pay")
@Entity
@Getter
@Builder
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payId")
    private long payId;     //결제번호

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;     //주문 번호

    @Column(name = "usedPoint", nullable = false)
    private int usedPoint;     //사용 포인트
    @Column(name = "payCompany", nullable = false)
    private String payCompany;     //카드사
    @Column(name = "cardNum", nullable = false)
    private String cardNum;     //카드일련번호
    @Column(name = "payPrice", nullable = false)
    private int payPrice;     //결제 금액
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;    //결제상태

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //결제일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //결제 수정일

    public Pay updatePay(OrderUpdateRequest orderUpdateRequest){
        this.usedPoint = orderUpdateRequest.getUsedPoint();
        this.payCompany = orderUpdateRequest.getPayCompany();
        this.cardNum = orderUpdateRequest.getCardNum();
        this.payPrice = orderUpdateRequest.getPayPrice();
        return this;
    }

    public Pay cancelOrder(OrderType orderType){
        this.orderType = orderType;
        return this;
    }


}
