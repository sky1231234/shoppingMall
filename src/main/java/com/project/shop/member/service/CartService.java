package com.project.shop.member.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.domain.Cart;
import com.project.shop.member.domain.Member;
import com.project.shop.member.dto.request.CartRequest;
import com.project.shop.member.dto.request.CartUpdateRequest;
import com.project.shop.member.dto.response.CartResponse;
import com.project.shop.member.repository.CartRepository;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository ;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository ;
    private final ItemImgRepository itemImgRepository ;
    private final OptionRepository optionRepository ;

    //회원별 장바구니 조회
    public List<CartResponse> cartFindByUserId(long userId){

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        List<Cart> cart = cartRepository.findAllByUsers(member);

        return cart.stream()
                .map(x -> {
                    var item = x.getItem();
                    Option option = optionRepository.findById(x.getOptionId())
                            .orElseThrow(() -> new RuntimeException("NOT_FOUND_OPTION"));

                    List<ItemImg> itemImg = itemImgRepository.findByItem(item);

                    return CartResponse.builder()
                            .itemId(item.getItemId())
                            .categoryName(item.getCategory().getCategoryName())
                            .brandName(item.getCategory().getBrandName())
                            .itemName(item.getItemName())
                            .itemSize(option.getSize())
                            .itemColor(option.getSize())
                            .count(x.getCount())
                            .itemThumbnail(
                                    itemImg.stream()
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

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        Item item = itemRepository.findById(cartRequest.itemId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

        Optional<Cart> cart = cartRepository.findByUsersAndAndItemAndOptionId(member,item,cartRequest.optionNum());

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

        if(cartRepository.findById(cartId).isEmpty()){
            throw new RuntimeException("NOT_FOUND_CART_ID");
        }

        cartRepository.deleteById(cartId);
    }

}
