package com.project.shop.member.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.item.builder.CategoryBuilder;
import com.project.shop.item.builder.ItemBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.builder.CartBuilder;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Cart;
import com.project.shop.member.dto.request.CartRequest;
import com.project.shop.member.dto.request.CartUpdateRequest;
import com.project.shop.member.dto.response.CartResponse;
import com.project.shop.member.repository.CartRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CartServiceTest extends ServiceCommon {

    @Autowired
    CartService cartService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OptionRepository optionRepository;

    Cart cart;
    Item item1; Item item2;
    Option option1; Option option2;
    @BeforeEach
    public void before(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        member2 = memberBuilder.signUpAdminMember();
        var memberSave = memberRepository.save(member1);
        var adminSave = memberRepository.save(member2);

        //auth
        Authority auth = memberBuilder.auth(memberSave);
        Authority authAdmin = memberBuilder.authAdmin(adminSave);
        authorityRepository.save(auth);
        authorityRepository.save(authAdmin);

        //category
        Category category = CategoryBuilder.createCategory1();
        categoryRepository.save(category);

        //item
        item1 = ItemBuilder.createItem1(category);
        item2 = ItemBuilder.createItem2(category);
        itemRepository.save(item1);
        itemRepository.save(item2);

        //option
        option1 = ItemBuilder.createOption1(item1);
        option2 = ItemBuilder.createOption2(item1);
        optionRepository.save(option1);
        optionRepository.save(option2);

        //cart
        cart = CartBuilder.createCart1(member1,item1);
        cartRepository.save(cart);
    }

    @Test
    @DisplayName("회원별 장바구니 조회")
    void cartFindDetailTest(){

        //given
        Cart cart1 = CartBuilder.createCart2(member1,item1);
        cartRepository.save(cart1);
        Cart cart2 = CartBuilder.createCart2(member2,item1);
        cartRepository.save(cart2);

        //when
        List<CartResponse> cartResponses = cartService.cartFindByUser(loginId);

        //then
        Assertions.assertThat(cartResponses.size()).isEqualTo(2);
        Assertions.assertThat(cartResponses.get(1).getCount()).isEqualTo(5);

    }
    
    @Test
    @DisplayName("장바구니 등록")
    void cartCreateTest(){

        //given
        CartRequest cartRequest1 = CartBuilder.createNewCart();
        CartRequest cartRequest2 = CartBuilder.createAlreadyCart();

        //when
        cartService.create(loginId,cartRequest1);
        cartService.create(loginId,cartRequest2);

        //then
        List<Cart> carts = cartRepository.findAllByMember(member1);
        Assertions.assertThat(carts.get(0).getCount()).isEqualTo(5);
        Assertions.assertThat(carts.get(1).getCount()).isEqualTo(2);

    }

    @Test
    @DisplayName("장바구니 수정")
    void cartUpdateTest(){

        //given
        CartUpdateRequest cartUpdateRequest = CartBuilder.createCartUpdateRequest();
        long cartId = 1;

        //when
        cartService.update(loginId,cartId, cartUpdateRequest);

        //then
        List<Cart> carts = cartRepository.findAllByMember(member1);
        Assertions.assertThat(carts.size()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getOptionId()).isEqualTo(2);

    }

    @Test
    @DisplayName("장바구니 삭제")
    void cartDeleteTest(){
        //given
        Cart cart1 = CartBuilder.createCart2(member1,item1);
        cartRepository.save(cart1);
        long cartId = 1;

        //when
        cartService.delete(loginId,cartId);

        //then
        List<Cart> carts = cartRepository.findAllByMember(member1);
        Assertions.assertThat(carts.size()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getCount()).isEqualTo(5);

    }

}
