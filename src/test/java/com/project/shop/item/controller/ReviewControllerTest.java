package com.project.shop.item.controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.shop.common.controller.ControllerCommon;
import com.project.shop.item.builder.CategoryBuilder;
import com.project.shop.item.builder.ItemBuilder;
import com.project.shop.item.builder.ReviewBuilder;
import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.request.ReviewUpdateRequest;
import com.project.shop.item.repository.*;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import com.project.shop.mock.WithCustomMockUser;
import com.project.shop.order.builder.OrderBuilder;
import com.project.shop.order.domain.Order;
import com.project.shop.order.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest extends ControllerCommon {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ReviewImgRepository reviewImgRepository;
    @Autowired
    ItemImgRepository itemImgRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    OrderRepository orderRepository;
    Item item1; Item item2;
    Order order1;

    @BeforeEach
    public void before(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        Member member1 = memberBuilder.signUpMember();
        var memberSave = memberRepository.save(member1);

        Authority auth = memberBuilder.auth(memberSave);
        authorityRepository.save(auth);

        //category
        Category category1 = CategoryBuilder.createCategory1();
        Category category2 = CategoryBuilder.createCategory2();
        Category category3 = CategoryBuilder.createCategory3();
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        //item
        item1 = ItemBuilder.createItem1(category1);
        item2 = ItemBuilder.createItem2(category2);
        itemRepository.save(item1);
        itemRepository.save(item2);

        ItemImg itemImg1 = ItemBuilder.createImg1(item1);
        itemImgRepository.save(itemImg1);

        Option option1 = ItemBuilder.createOption1(item1);
        Option option2 = ItemBuilder.createOption2(item1);
        Option option3 = ItemBuilder.createOption3(item1);
        optionRepository.save(option1);
        optionRepository.save(option2);
        optionRepository.save(option3);

        //order
        order1 = OrderBuilder.createOrder(member1);
        orderRepository.save(order1);

        //review
        Review review = ReviewBuilder.createReview(member1,item1);
        reviewRepository.save(review);

        //reviewImg
        ReviewImg reviewImg = ReviewBuilder.createReviewImg(review);
        reviewImgRepository.save(reviewImg);


    }

    @Test
    @DisplayName("상품-리뷰 조회")
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    void itemReviewFindAll() throws Exception {

        //given
        Review review = ReviewBuilder.createReview2(member1,item1);
        reviewRepository.save(review);

        ReviewImg reviewImg = ReviewBuilder.createReviewImg(review);
        reviewImgRepository.save(reviewImg);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/items/{itemId}/reviews",1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.reviewList.*",hasSize(2)))
                .andExpect(jsonPath("$.reviewList[1].reviewStar").value(5));


    }
    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("회원-리뷰 조회")
    void memberReviewFindAll() throws Exception {
        //given
        Review review = ReviewBuilder.createReview2(member2,item1);
        reviewRepository.save(review);
        ReviewImg reviewImg = ReviewBuilder.createReviewImg(review);
        reviewImgRepository.save(reviewImg);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/reviews"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.reviewItemList.*",hasSize(1)))
                .andExpect(jsonPath("$.reviewItemList[0].reviewTitle").value("리뷰 제목"));

    }
    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("리뷰 상세 조회")
    void reviewDetailFind() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reviews/{reviewId}",1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.thumbnail.url").value("itemImg1"))
                .andExpect(jsonPath("$.reviewImgResponses[0].url").value("urlurl"));

    }

    @Test
    @DisplayName("리뷰 등록")
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    void reviewCreate() throws Exception {
        //given
        ReviewRequest reviewRequest = ReviewBuilder.createReviewRequest(item1,order1);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews")
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(reviewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //then
        Assertions.assertThat(reviewRepository.findById(1L).get().getStar()).isEqualTo(4);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("리뷰 수정")
    void reviewUpdate() throws Exception {
        //given
        ReviewUpdateRequest reviewUpdateRequest = ReviewBuilder.createReviewUpdateRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/reviews/{reviewId}",1)
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(reviewUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        Assertions.assertThat(reviewRepository.findById(1L).get().getContent()).isEqualTo("상품 수정 좋아요");
     }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("리뷰 삭제")
    void reviewDelete() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reviews/{reviewId}",1))
                .andExpect(status().isNoContent());

        //then
        Assertions.assertThat(reviewRepository.findAll().size()).isEqualTo(0);

    }

}
