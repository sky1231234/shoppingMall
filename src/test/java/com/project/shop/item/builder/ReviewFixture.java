package com.project.shop.item.builder;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.item.domain.ReviewImg;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.request.ReviewUpdateRequest;
import com.project.shop.member.domain.Member;
import com.project.shop.order.domain.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


public class ReviewFixture {

    public static ArrayList<String> createImgList1(){

        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("item1_img1");
        imgList.add("item1_img2");

        return imgList;
    }

    public static ArrayList<String> createImgList2(){

        ArrayList<String> imgList = new ArrayList<>();
        imgList.add("item1_img1");
        imgList.add("item1_img2");
        imgList.add("item2_img3");

        return imgList;
    }

    public static Review createReview(Member member, Item item){

        return Review.builder()
                .item(item)
                .member(member)
                .title("리뷰 제목")
                .content("신발이 예뻐요")
                .star(2)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
    public static Review createReview2(Member member, Item item){

        return Review.builder()
                .item(item)
                .member(member)
                .title("후기"+item.getItemId())
                .content("사이즈가 잘 맞아요"+item.getItemId())
                .star(5)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static ReviewImg createReviewImg(Review review){

        return ReviewImg.builder()
                .review(review)
                .imgUrl("urlurl")
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static ReviewRequest createReviewRequest(Item item, Order order){

        return new ReviewRequest(
                item.getItemId(),
                order.getOrderId(),
                "리뷰1",
                "상품 좋아요",
                4,
                List.of("img1","img2")
        );
    }

    public static ReviewUpdateRequest createReviewUpdateRequest(){

        return new ReviewUpdateRequest(
                "리뷰 수정",
                "상품 수정 좋아요",
                5,
                List.of("img11","url22","imgurl33")
        );
    }

}
