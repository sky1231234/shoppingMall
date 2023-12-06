package com.project.shop.user.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Option;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.user.domain.Cart;
import com.project.shop.user.domain.User;
import com.project.shop.user.dto.request.CartRequest;
import com.project.shop.user.dto.request.CartUpdateRequest;
import com.project.shop.user.dto.response.CartResponse;
import com.project.shop.user.repository.CartRepository;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository ;
    private final UserRepository userRepository ;
    private final ItemRepository itemRepository ;
    private final OptionRepository optionRepository ;

    //회원별 장바구니 조회
    public List<CartResponse> cartFindByUserId(long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        List<Cart> cart = cartRepository.findAllByUsers(user);

        return cart.stream()
                .map(x -> {
                    var item = x.getItem();
                    Option option = optionRepository.findById(x.getOptionId())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                    return CartResponse.builder()
                            .itemId(item.getItemId())
                            .categoryName(item.getCategory().getCategoryName())
                            .brandName(item.getCategory().getBrandName())
                            .itemName(item.getItemName())
                            .itemSize(option.getSize())
                            .itemColor(option.getSize())
                            .count(x.getCount())
                            .itemThumbnail(item.getItemImgList().stream()
                                    .filter(y -> y.getItemImgType() == ItemImgType.Y)
                                    .map(y -> {
                                        return CartResponse.Thumbnail.builder()
                                                .imgId(y.getItemImgId())
                                                .url(y.getImgUrl())
                                                .build();
                                    })
                                    .findFirst().orElse(null)
                            )
                            .build();
                })
                .toList();

    }

    //장바구니 등록
    public void create(long userId, CartRequest cartRequest){

        //해당 회원이 장바구니 등록해놓은게 있는지 확인

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        Item item = itemRepository.findById(cartRequest.itemId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

        Optional<Cart> cart = cartRepository.findByUsersAndAndItemAndOptionId(user,item,cartRequest.optionNum());

//              등록한게 있으면 수량 +1
                if(cart.isPresent()){
                        Cart count = cart.get().updateCount();
                        cartRepository.save(count);
                }else{
                    //등록된 장바구니 없으면 새로 등록
                    Item newCart = itemRepository.findById(cart.get().getItem().getItemId())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));
                    cartRepository.save(cartRequest.toEntity(newCart));
                }
    }

    //장바구니 수정
    public void update(long cartId, CartUpdateRequest cartUpdateRequest){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CART_ID"));

        Cart update = cart.updateCart(cartUpdateRequest);
        cartRepository.save(update);
    }

    //장바구니 삭제
    public void delete(long cartId){

        if(!cartRepository.findById(cartId).isPresent()){
            throw new RuntimeException("NOT_FOUND_CART_ID");
        }

        cartRepository.deleteById(cartId);
    }

}
