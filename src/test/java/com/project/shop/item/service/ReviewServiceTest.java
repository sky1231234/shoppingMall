package com.project.shop.item.service;

import com.project.shop.item.data.CategoryData;
import com.project.shop.item.data.ItemData;
import com.project.shop.item.data.ReviewData;
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
import com.project.shop.user.Data.UserData;
import com.project.shop.user.domain.User;
import com.project.shop.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
    UserRepository userRepository;

    static Item item1;
    static Item item2;
    static User user1;
    static User user2;


    @BeforeEach
    public void before(){

        //user
        user1 = UserData.createUser1();
        user2 = UserData.createUser2();
        userRepository.save(user1);
        userRepository.save(user2);

        //category
        Category category = CategoryData.createCategory1();
        categoryRepository.save(category);

        //item
        item1 = ItemData.createItem1(category);
        item2 = ItemData.createItem2(category);
        itemRepository.save(item1);
        itemRepository.save(item2);

    }

    @Test
    @DisplayName("상품-리뷰 조회")
    void categoryCreateTest(){

        //리뷰 등록
        var review = createReview1();
        createReview2();

        //상품의 다른 리뷰 조회
        ItemReviewResponse itemReviewResponse = reviewService.itemReviewFindAll(review);

        Assertions.assertThat(itemReviewResponse.getReviewList().size()).isEqualTo(2);
        Assertions.assertThat(itemReviewResponse.getCategoryName()).isEqualTo("운동화");
        Assertions.assertThat(itemReviewResponse.getReviewList().get(0).getReviewContent())
                .isEqualTo("나이키 좋아요");

    }

    @Test
    @DisplayName("회원-리뷰 조회")
    void categoryFindAllTest(){

        //리뷰 등록
        createReview1();
        createReview2();
        createReview3();

        //회원별 리뷰 조회
        UserReviewResponse userReviewResponse = reviewService.userReviewFindAll(user1.getUserId());
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
        ArrayList<String> imgList = ReviewData.createImgList2();
        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest(user1, item1, "나이키 비추", "나이키 싫어요", 1,imgList);
        reviewService.update(review,reviewUpdateRequest);

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
        ArrayList<String> imgList = ReviewData.createImgList1();
        ReviewRequest reviewRequest = new ReviewRequest(user1, item1, "나이키 후기", "나이키 좋아요", 5,imgList);

        //when
        return reviewService.create(reviewRequest);
    }

    private void createReview2(){

        //given
        ArrayList<String> imgList = ReviewData.createImgList2();
        ReviewRequest reviewRequest = new ReviewRequest(user2, item1, "뉴발란스 후기", "뉴발란스", 1,imgList);

        //when
        reviewService.create(reviewRequest);
    }

    private void createReview3(){

        //given
        ArrayList<String> imgList = ReviewData.createImgList2();
        ReviewRequest reviewRequest = new ReviewRequest(user1, item1, "뉴발란스 후기", "뉴발란스", 1,imgList);

        //when
        reviewService.create(reviewRequest);
    }
}
