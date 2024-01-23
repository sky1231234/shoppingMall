package com.project.shop.member.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.member.domain.Cart;
import com.project.shop.member.domain.Member;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CartRequest(
        @NotNull long itemId, // 상품 번호
        @NotNull int count, // 구매 수량
        @NotNull long optionNum// 옵션 번호
        ) {

        public Cart toEntity(Item item, Member member){
                return Cart.builder()
                        .item(item)
                        .member(member)
                        .count(this.count())
                        .optionId(this.optionNum())
                        .insertDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();
        }

}
