package com.project.shop.item.controller;

import com.project.shop.item.Builder.CategoryBuilder;
import com.project.shop.item.Builder.ItemBuilder;
import com.project.shop.item.Builder.ReviewBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewImgRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.item.service.ReviewService;
import com.project.shop.member.Builder.MemberBuilder;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ReviewService reviewService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewImgRepository reviewImgRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MemberRepository memberRepository;

    static Item item1;
    static Item item2;
    static Member member1;
    static Member member2;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void before(){

        //user
        member1 = MemberBuilder.createUser1();
        member2 = MemberBuilder.createUser2();
        memberRepository.save(member1);
        memberRepository.save(member2);

        //category
        Category category = CategoryBuilder.createCategory1();
        categoryRepository.save(category);

        //item
        item1 = ItemBuilder.createItem1(category);
        item2 = ItemBuilder.createItem2(category);
        itemRepository.save(item1);
        itemRepository.save(item2);

    }

    @Test
    @DisplayName("상품-리뷰 조회")
    void itemReviewFindAll() throws Exception {

        //given
        var review = createReview1();
        createReview2();

        //when
//        ItemReviewResponse itemReviewResponse = reviewService.itemReviewFindAll(review);

        mvc.perform(MockMvcRequestBuilders.get("/items/"+review+"/reviews"))
                .andExpect(status().isOk()); //200 상태

        //then
//        Assertions.assertThat(itemReviewResponse.getReviewList().size()).isEqualTo(2);
//        Assertions.assertThat(itemReviewResponse.getCategoryName()).isEqualTo("운동화");
//        Assertions.assertThat(itemReviewResponse.getReviewList().get(0).getReviewContent())
//                .isEqualTo("나이키 좋아요");

    }
    @Test
    @DisplayName("회원-리뷰 조회")
    void userReviewFindAll() throws Exception {

    }

    @Test
    @DisplayName("리뷰 등록")
    void create() throws Exception {
        //given
        var review = createReview1();
        createReview2();

        //when
//        List<Review> list = reviewRepository.findAll();
        var result = mvc.perform(MockMvcRequestBuilders.get("/reviews"))
                .andExpect(status().isOk())
                .andReturn(); //200 상태

        var result1 = result.getResponse().getContentAsString();
        if(result1 != null)
            System.out.println(result1);

        //then
//        Assertions.assertThat(list.size()).isEqualTo(2);
//        Assertions.assertThat(review).isEqualTo(1);
    }
    private long createReview1(){

        //given
        ArrayList<String> imgList = ReviewBuilder.createImgList1();
//        member1
        ReviewRequest reviewRequest = new ReviewRequest(item1, "나이키 후기", "나이키 좋아요", 5,imgList);

        //when
        return reviewService.create(any(),reviewRequest);
    }

    private void createReview2(){

        //given
        ArrayList<String> imgList = ReviewBuilder.createImgList2();
        //member2
        ReviewRequest reviewRequest = new ReviewRequest(item1, "뉴발란스 후기", "뉴발란스", 1,imgList);

        //when
        reviewService.create(any(),reviewRequest);
    }

}
