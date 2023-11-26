package com.project.shop.user.dto.request;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.user.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public record CartRequest(
        @NotNull long itemId, // 상품 번호
        @NotNull int count, // 구매 수량
        @NotNull long optionNum// 옵션 번호
        ) {

        public Cart toEntity(Item item){
                return Cart.builder()
                        .item(item)
                        .count(this.count)
                        .optionId(this.optionNum)
                        .build();
        }

}
