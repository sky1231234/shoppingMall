package com.project.shop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "payCancel")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayCancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long payCancelId;     //결제취소번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;     //주문 번호

    @Column(name = "orderNum", nullable = false)
    private String orderNum;     //주문 비즈니스키

    @Column(name = "payCompany", nullable = false)
    private String payCompany;     //카드사
    @Column(name = "cardNum", nullable = false)
    private String cardNum;     //카드일련번호
    @Column(name = "cancelReason", nullable = false)
    private String cancelReason;     //취소사유
    @Column(name = "payPrice", nullable = false)
    private int payPrice;     //취소 금액
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PayCancelType payCancelType;    //취소상태

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //결제일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //결제 수정일

}
