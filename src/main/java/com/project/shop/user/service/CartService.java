package com.project.shop.user.service;

import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.user.domain.Cart;
import com.project.shop.user.dto.request.CartRequest;
import com.project.shop.user.dto.request.CartUpdateRequest;
import com.project.shop.user.dto.response.CartResponse;
import com.project.shop.user.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository ;
    private final ItemImgRepository itemImgRepository ;
    private final ItemRepository itemRepository ;
    private final OptionRepository optionRepository ;

    //회원별 장바구니 조회
    public List<CartResponse> cartFindByUserId(long userId){

        List<Cart> carts = cartRepository.findAllByUserId(userId);

        for (Cart cart : carts) {
            var item = itemRepository.findById(cart.getItemId());
            var mainImg = itemImgRepository.findByItemIdAndMainImg(cart.getItemId(),"Y");
            var option = optionRepository.findById(cart.getOptionId());

            //cart + item + mainImg + option
            CartResponse.builder()
                    .itemId(cart.getItem().getItemId())
                    .itemName(cart.getItem().getItemName())
                    .itemThumbnail(mainImg)
                    .itemSize()
                    .itemColor(option)
                    .count(cart.getCount())
                    .build();
        }

        return carts
                .stream()
                .map(CartResponse.fromEntity(cartData))
                .collect(Collectors.toList());

    }

    //장바구니 등록
    public void create(CartRequest cartRequest){

        //해당 회원이 장바구니 등록해놓은게 있는지 확인
        if(cartRepository.findByUserIdAndOptionNum(cartRequest.getItemId(),cartRequest.getOptionNum()).isPresent())
            throw new RuntimeException("NOT_FOUND_CART");

        //item repository

        // 등록되어있는 장바구니 없으면 새로 등록
        cartRepository.save(cartRequest.toEntity());

    }

    //장바구니 수정
    public void update(long cartId, CartUpdateRequest cartUpdateRequest){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CARTID"));

        Cart cart1 = cart.updateCart(cartUpdateRequest);

        cartRepository.save(cart1);
    }

    //장바구니 삭제
    public void delete(long cartId){

        if(!cartRepository.findById(cartId).isPresent()){
            throw new RuntimeException("NOT_FOUND_CARTID");
        }

        cartRepository.deleteById(cartId);
    }

}
