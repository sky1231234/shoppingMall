package com.project.shop.item.service;

import com.project.shop.item.Builder.CategoryBuilder;
import com.project.shop.item.Builder.ItemBuilder;
import com.project.shop.item.Builder.ReviewBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.item.domain.ReviewImg;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewImgRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.member.Builder.MemberBuilder;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.MemberRepository;
import com.project.shop.order.Builder.OrderBuilder;
import com.project.shop.order.domain.Order;
import com.project.shop.order.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
//@Transactional
public class ReviewServiceTest {

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
    @Autowired
    OrderRepository orderRepository;

    Item item1; Item item2;
    Member member1; Member member2;
    Order order1;
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

        //order
        order1 = OrderBuilder.createOrder(member1);
        orderRepository.save(order1);

    }

    @Test
    @DisplayName("상품-리뷰 조회")
    void categoryCreateTest(){

        //given
        var review = createReview1();
        createReview2();

        //when
        ItemReviewResponse itemReviewResponse = reviewService.itemReviewFindAll(review);

        //then
        Assertions.assertThat(itemReviewResponse.getReviewList().size()).isEqualTo(2);
        Assertions.assertThat(itemReviewResponse.getCategoryName()).isEqualTo("운동화");
        Assertions.assertThat(itemReviewResponse.getReviewList().get(0).getReviewContent())
                .isEqualTo("나이키 좋아요");

    }

    @Test
    @DisplayName("회원-리뷰 조회")
    void categoryFindAllTest(){

        //given
        createReview1();
        createReview2();
        createReview3();

        //when
        UserReviewResponse userReviewResponse = reviewService.userReviewFindAll(any());

        //then
        Assertions.assertThat(userReviewResponse.getReviewItemList().size()).isEqualTo(2);
        Assertions.assertThat(userReviewResponse.getReviewItemList()
                        .get(0).getReviewTitle())
                .isEqualTo("나이키 후기");

    }

    @Test
    @DisplayName("리뷰 등록")
    void reviewCreateTest(){

        //given
        var review = createReview1();
        createReview2();
        createReview3();

        //when
        List<Review> list = reviewRepository.findAll();

        //then
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(review).isEqualTo(1);

    }

    @Test
    @DisplayName("리뷰 수정")
    void categoryUpdateTest(){

        //given
        var review = createReview1();
        createReview2();
        createReview3();

        //when
        ArrayList<String> imgList = ReviewBuilder.createImgList2();
        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest("나이키 비추", "나이키 싫어요", 1,imgList);
        reviewService.update(any(),review,reviewUpdateRequest);

        //then
        Review list = reviewRepository.findById(review)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_REVIEW"));

        List<ReviewImg> reviewImg = reviewImgRepository.findByReview(list);
        Assertions.assertThat(list.getContent()).isEqualTo("나이키 싫어요");
        Assertions.assertThat(list.getStar()).isEqualTo(1);
        Assertions.assertThat(reviewImg.size()).isEqualTo(3);


    }

    private long createReview1(){

        //given
        ArrayList<String> imgList = ReviewBuilder.createImgList1();
        //member1
        ReviewRequest reviewRequest = new ReviewRequest(item1.getItemId(),order1.getOrderId(), "나이키 후기", "나이키 좋아요", 5,imgList);

        //when
        return reviewService.create(any(),reviewRequest);
    }

    private void createReview2(){

        //given
        ArrayList<String> imgList = ReviewBuilder.createImgList2();
        //member2
        ReviewRequest reviewRequest = new ReviewRequest(item1.getItemId(),order1.getOrderId(), "뉴발란스 후기", "뉴발란스", 1,imgList);

        //when
        reviewService.create(any(),reviewRequest);
    }

    private void createReview3(){

        //given
        ArrayList<String> imgList = ReviewBuilder.createImgList2();
        //member1
        ReviewRequest reviewRequest = new ReviewRequest(item1.getItemId(),order1.getOrderId(), "뉴발란스 후기", "뉴발란스", 1,imgList);

        //when
        reviewService.create(any(),reviewRequest);
    }
}
