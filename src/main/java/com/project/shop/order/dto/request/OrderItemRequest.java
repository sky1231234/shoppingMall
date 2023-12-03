package com.project.shop.order.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record OrderItemRequest(

//{
//
//              "itemId" : "상품id",
//              "itemCount" : "주문상품 수량",
//              "itemPrice" : "상품 총 가격"
//              "itemSize" : "주문상품 사이즈",
//              "itemColor" : "주문상품 색상",
//}

        @NotNull long itemId,
        @NotNull int itemCount,
        @NotNull int itemPrice,
        @NotBlank String itemSize,
        @NotBlank String itemColor

        ) {

        //orderRequest -> orderItem
        public OrderItem toEntity(Item item, Order order, Option option){
            return OrderItem.builder()
                    .item(item)
                    .order(order)
                    .totalQuantity(this.getItemCount())
                    .itemPrice(this.getItemPrice())
                    .itemOptionId(option.getOptionId())
                    .build();
        }



}
