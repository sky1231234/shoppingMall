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

    //장바구니 조회
    public List<CartResponse> cartFindByUser(String loginId){

        Member member = findLoginMember(loginId);

        List<Cart> cart = cartRepository.findAllByMember(member);

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
                            .itemColor(option.getColor())
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
    public void create(String loginId, CartRequest cartRequest){

        Member member = findLoginMember(loginId);

        Item item = itemRepository.findById(cartRequest.itemId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

        //해당 회원이 장바구니 등록해놓은게 있는지 확인
        Optional<Cart> cart = cartRepository.findByMemberAndAndItemAndOptionId(member,item,cartRequest.optionNum());

            //등록한게 있으면 수량+
            if(cart.isPresent()){
                    Cart count = cart.get().updateCount(cartRequest.count());
                    cartRepository.save(count);
            }else{
                //등록된 장바구니 없으면 새로 등록
                cartRepository.save(cartRequest.toEntity(item,member));
            }
    }

    //장바구니 수정
    public void update(String loginId, long cartId, CartUpdateRequest cartUpdateRequest){

        Member member = findLoginMember(loginId);
        Cart cart = cartFindById(cartId);
        equalLoginMemberCheck(member, cart);

        Cart update = cart.updateCart(cartUpdateRequest);
        cartRepository.save(update);
    }

    //장바구니 삭제
    public void delete(String loginId, long cartId){

        Member member = findLoginMember(loginId);
        Cart cart = cartFindById(cartId);
        equalLoginMemberCheck(member, cart);

        cartRepository.deleteById(cartId);
    }

    //로그인 member 확인
    private Member findLoginMember(String loginId){

        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
    }

    //cart 확인
    private Cart cartFindById(long cartId){

        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CART"));
    }

    //로그인 member와 cart member 비교
    private void equalLoginMemberCheck(Member member, Cart cart){
        if( ! member.equals(cart.getMember()) )
            throw new RuntimeException("NOT_EQUAL_CART_MEMBER");
    }



}
