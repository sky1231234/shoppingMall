package com.project.shop.item.domain;

import com.project.shop.item.dto.request.ItemRequest;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ItemDomain {

    private final int MIN_ITEM_PRICE = 1;
    private final String ITEM_PRICE_IF_NOT_POSITIVE_MESSAGE =  "상품의 가격은 1원 이상여야한다.";

    public void validateItemPrice(int itemPrice){
        if(itemPrice < MIN_ITEM_PRICE){
            throw new IllegalArgumentException(ITEM_PRICE_IF_NOT_POSITIVE_MESSAGE);
        }
    }

    public Item toItem(Category category, ItemRequest itemRequest) {
        return itemRequest.toEntity(category);
    }

}
