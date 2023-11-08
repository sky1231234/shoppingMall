package com.project.shop.order.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "pay")
@Entity
public class PayCancle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payCancleId")
    private long payCancleId;     //결제취소번호
    @Column(name = "orderId", nullable = false)
    private long orderId;     //주문 번호
    @Column(name = "usedPoint", nullable = false)
    private int usedPoint;     //취소 포인트
    @Column(name = "payCompany", nullable = false)
    private String payCompany;     //카드사
    @Column(name = "cardNum", nullable = false)
    private String cardNum;     //카드일련번호
    @Column(name = "cancleReason", nullable = false)
    private String cancleReason;     //취소사유
    @Column(name = "payPrice", nullable = false)
    private int payPrice;     //취소 금액
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private payCancleType payCancleType;    //취소상태

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //결제일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //결제 수정일

}
