package com.project.shop.ordersheet.domain;

import com.project.shop.member.domain.Address;
import com.project.shop.ordersheet.exception.OrderSheetException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Table(name = "orderSheets")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long orderSheetId;     //주문번호

    @OneToMany(mappedBy = "orderSheet", cascade = CascadeType.ALL)
    private List<OrderSheetItem> orderSheetItems;

    @Column(name = "usedPoint", nullable = false)
    private int usedPoint;
    @Column(name = "itemSumPrice", nullable = false)
    private int itemSumPrice;
    @Column(name = "deliverFee", nullable = false)
    private int deliverFee;
    @Column(name = "finalPrice", nullable = false)
    private int finalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressId")
    private Address address;

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;

    @Builder
    public OrderSheet(List<OrderSheetItem> orderSheetItems,
                      int usedPoint, int itemSumPrice, int finalPrice,
                      int deliverFee, Address address,
                      LocalDateTime dateTime) {
        this.orderSheetItems = orderSheetItems;
        this.usedPoint = usedPoint;
        this.itemSumPrice = itemSumPrice;
        this.finalPrice = finalPrice;
        this.deliverFee = deliverFee;
        this.address = address;
        this.insertDate = dateTime;
        this.updateDate = dateTime;
    }

    public void addOrderSheetItem(List<OrderSheetItem> orderSheetItems){
        this.orderSheetItems = orderSheetItems;
    }

    public int sumAllPriceAndAmount(List<OrderSheetItem> orderSheetItems){
        for (OrderSheetItem item : orderSheetItems
             ) {
            this.itemSumPrice += item.getItemPrice() * item.getOrderQuantity();
        }
        return this.itemSumPrice;
    }

    public int checkAvailablePoint(int itemSumPrice, int point, int deliverFee){

        if(itemSumPrice < point)
            throw new RuntimeException(OrderSheetException.EXCEED_AVAILABLE_POINT.getMessage());

        return calculateTotalPriceAfterPoints(itemSumPrice, point, deliverFee);
    }

    public int calculateTotalPriceAfterPoints(int itemSumPrice, int point, int deliverFee){
        this.finalPrice = itemSumPrice + deliverFee - point;
        return this.finalPrice;
    }


}
