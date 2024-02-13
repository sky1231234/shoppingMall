package com.project.shop.member.builder;

import com.project.shop.item.domain.Item;
import com.project.shop.member.domain.Cart;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.CartRequest;
import com.project.shop.member.dto.request.CartUpdateRequest;

import java.time.LocalDateTime;

public class CartBuilder {

    public static Cart createCart1(Member member, Item item){

        return Cart.builder()
                .member(member)
                .item(item)
                .optionId(1)
                .count(3)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static Cart createCart2(Member member, Item item){

        return Cart.builder()
                .member(member)
                .item(item)
                .optionId(2)
                .count(5)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static CartRequest createNewCart(){

        return new CartRequest(
                1L,
                2,
                2L
                );
    }

    public static CartRequest createAlreadyCart(){

        return new CartRequest(
                1L,
                2,
                1L
        );
    }

    public static CartUpdateRequest createCartUpdateRequest(){

        return new CartUpdateRequest(
                1,
                2L
        );
    }


}
