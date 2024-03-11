package com.project.shop.item.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.item.builder.CategoryFixture;
import com.project.shop.item.builder.ItemFixture;
import com.project.shop.item.builder.ReviewFixture;
import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.item.repository.*;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.order.builder.OrderBuilder;
import com.project.shop.order.domain.Order;
import com.project.shop.order.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class ReviewServiceTest extends ServiceCommon {

    @Autowired
    ReviewService reviewService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemImgRepository itemImgRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewImgRepository reviewImgRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OrderRepository orderRepository;

    Item item1; Item item2;
    Order order1;
    LocalDateTime now = LocalDateTime.now();
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
        Category category = CategoryFixture.createCategory(1L, "운동화", "나이키", now );
        categoryRepository.save(category);

        //item
        item1 = ItemFixture.createItem1(category);
        item2 = ItemFixture.createItem2(category);
        itemRepository.save(item1);
        itemRepository.save(item2);

        ItemImg itemImg1 = ItemFixture.createImg1(item1);
        itemImgRepository.save(itemImg1);

        Option option1 = ItemFixture.createOption1(item1);
        Option option2 = ItemFixture.createOption2(item1);
        Option option3 = ItemFixture.createOption3(item1);
        optionRepository.save(option1);
        optionRepository.save(option2);
        optionRepository.save(option3);

        //order
        order1 = OrderBuilder.createOrder(member1);
        orderRepository.save(order1);

        //review
        Review review1 = ReviewFixture.createReview(member1,item1);
        Review review2= ReviewFixture.createReview2(member1,item2);
        Review review3= ReviewFixture.createReview2(member2,item1);
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        //reviewImg
        ReviewImg reviewImg = ReviewFixture.createReviewImg(review1);
        reviewImgRepository.save(reviewImg);

    }

    @Test
    @DisplayName("상품-리뷰 조회")
    void categoryCreateTest(){

        //given

        //when
        ItemReviewResponse itemReviewResponse = reviewService.findAllByItem(item1.getItemId());

        //then
        Assertions.assertThat(itemReviewResponse.getReviewList().size()).isEqualTo(2);
        Assertions.assertThat(itemReviewResponse.getReviewList().get(1).getReviewContent())
                .isEqualTo("사이즈가 잘 맞아요1");

    }

    @Test
    @DisplayName("회원-리뷰 조회")
    void categoryFindAllTest(){

        //given

        //when
        UserReviewResponse userReviewResponse = reviewService.findAllByMember(member1.getLoginId());

        //then
        Assertions.assertThat(userReviewResponse.getReviewItemList().size()).isEqualTo(2);
        Assertions.assertThat(userReviewResponse.getReviewItemList()
                        .get(1).getReviewTitle())
                .isEqualTo("후기1");

    }

    @Test
    @DisplayName("리뷰 상세 조회")
    void reviewDetailFind(){

        //given
        long reviewId = 1;

        //when
        ReviewResponse reviewResponse = reviewService.detailFind(reviewId);

        //then
        Assertions.assertThat(reviewResponse.getReviewTitle()).isEqualTo("리뷰 제목");
        Assertions.assertThat(reviewResponse.getReviewImgResponses().get(0).getUrl())
                .isEqualTo("urlurl");

    }

    @Test
    @DisplayName("리뷰 등록")
    void reviewCreate(){

        //given
        ReviewRequest reviewRequest = ReviewFixture.createReviewRequest(item2,order1);

        //when
        var reviewId = reviewService.create(member1.getLoginId(), reviewRequest);


        //then
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_REVIEW_SERVICE_TEST"));

        Assertions.assertThat(review).isEqualTo(4);
        Assertions.assertThat(reviewImgRepository.findByReview(review).get(1)).isEqualTo("img2");

    }

    @Test
    @DisplayName("리뷰 수정")
    void categoryUpdate(){

        //given
        ReviewUpdateRequest reviewUpdateRequest = ReviewFixture.createReviewUpdateRequest();

        //when
        reviewService.update(member1.getLoginId(),1,reviewUpdateRequest);

        //then
        Review review = reviewRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_REVIEW"));

        List<ReviewImg> reviewImg = reviewImgRepository.findByReview(review);
        Assertions.assertThat(review.getContent()).isEqualTo("상품 수정 좋아요");
        Assertions.assertThat(review.getStar()).isEqualTo(5);
        Assertions.assertThat(reviewImg.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("리뷰 삭제")
    void categoryDelete(){

        //given
        long reviewId = 1;

        //when
        reviewService.delete(member1.getLoginId(),reviewId);

        //then
        Assertions.assertThat(reviewRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(reviewImgRepository.findAll().size()).isEqualTo(0);

    }

}
