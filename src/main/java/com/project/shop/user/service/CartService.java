package com.project.shop.user.service;

import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.user.domain.Cart;
import com.project.shop.user.dto.request.CartRequest;
import com.project.shop.user.dto.request.CartUpdateRequest;
import com.project.shop.user.dto.response.CartResponse;
import com.project.shop.user.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository ;
    private final ItemImgRepository itemImgRepository ;

    //회원별 장바구니 조회
    public List<CartResponse> cartFindByUserId(long userId){

        List<Cart> carts = cartRepository.findAllByUserId(userId);

        for (Cart cart : carts) {
            var mainImg = itemImgRepository.findByItemIdAndMainImg(cart.getItemId(),"Y");
        }

        return carts
                .stream()
                .map(CartResponse.fromEntity(cart, mainImg))
                .collect(Collectors.toList());

    }

    //장바구니 등록
    public void create(CartRequest cartRequest){

        //해당 회원이 장바구니 등록해놓은게 있는지 확인
        if(cartRepository.findByUserIdAndOptionNum(cartRequest.getItemId(),cartRequest.getOptionNum()).isPresent())
            throw new RuntimeException("NOT_FOUND_CART");

        // 등록되어있는 장바구니 없으면 새로 등록
        cartRepository.save(cartRequest.toEntity());

    }

    //장바구니 수정
    public void update(long cartId, CartUpdateRequest cartUpdateRequest){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CARTID"));

        cart.updateCart(cartUpdateRequest);
    }

    //장바구니 삭제
    public void delete(long cartId){

        if(!cartRepository.findById(cartId).isPresent()){
            throw new RuntimeException("NOT_FOUND_CARTID");
        }

        cartRepository.deleteById(cartId);
    }

}
