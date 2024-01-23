package com.project.shop.item.Builder;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.item.domain.ReviewImg;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.request.ReviewUpdateRequest;
import com.project.shop.member.domain.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


public class ReviewBuilder {

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
                .title("후기")
                .content("사이즈가 잘 맞아요")
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

    public static ReviewRequest createReviewRequest(Item item){

        return new ReviewRequest(
                item,
                "리뷰1",
                "상품 좋아요",
                4,
                List.of("img1","img2")
        );
    }

    public static ReviewUpdateRequest createReviewUpdateRequest(Member member, Item item){

        return new ReviewUpdateRequest(
                member,
                item,
                "리뷰 수정",
                "상품 수정 좋아요",
                5,
                List.of("img11")
        );
    }

//    private long createReview1(){
//
//        //given
//        ArrayList<String> imgList = ReviewBuilder.createImgList1();
////        member1
//        ReviewRequest reviewRequest =
//                new ReviewRequest(item1, "나이키 후기", "나이키 좋아요", 5,imgList);
//
//        //when
//        return reviewService.create(any(),reviewRequest);
//    }
//
//    private void createReview2(){
//
//        //given
//        ArrayList<String> imgList = ReviewBuilder.createImgList2();
//        //member2
//        ReviewRequest reviewRequest = new ReviewRequest(item1, "뉴발란스 후기", "뉴발란스", 1,imgList);
//
//        //when
//        reviewService.create(any(),reviewRequest);
//    }


}
