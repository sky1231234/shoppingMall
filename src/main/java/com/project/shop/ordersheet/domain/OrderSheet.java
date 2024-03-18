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

    public int applyPoints(int itemSumPrice, int point) {

        if(itemSumPrice < point)
            throw new RuntimeException(OrderSheetException.EXCEED_AVAILABLE_POINT.getMessage() + itemSumPrice);

        return itemSumPrice - point;
    }

    public int calculateTotalPriceAfterPoints(int totalPriceAfterPoints, int deliverFee){
        this.finalPrice = totalPriceAfterPoints + deliverFee;
        return this.finalPrice;
    }


//    public int calculateItemSumPrice(List<OrderItemRequest> orderItemRequestList) {
//
//        return orderItemRequestList.stream()
//                .mapToInt(item -> item.itemPrice() * item.itemCount())
//                .sum();
//    }
//
//    public int calculateDeliverFee(int itemSumPrice){
//
//        if(itemSumPrice >= 50000)
//            return 0;
//        else  return 2500;
//
//    }
//
//    public int calculateTotalPrice(int itemSumPrice, int deliverFee, int usingPoint) {
//
//        int totalPrice = itemSumPrice + deliverFee;
//        int finalPrice = totalPrice - usingPoint;
//
//        if(finalPrice < 0)
//            throw new RuntimeException(PointException.AVAILABLE_MAXIMUM_POINT.getMessage() + totalPrice );
//
//        return finalPrice;
//    }
}
