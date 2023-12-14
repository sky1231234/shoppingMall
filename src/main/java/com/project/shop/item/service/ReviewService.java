package com.project.shop.item.service;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.*;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewImgRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.user.domain.User;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;

    //상품 - 리뷰 조회
    public ItemReviewResponse itemReviewFindAll(long itemId){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUNT_ITEM"));

        List<Review> reviewList = reviewRepository.findAllByItem(item);
        List<ItemImg> itemImgList = itemImgRepository.findByItem(item);

        var list = reviewList.stream().map(x -> {
            return ItemReviewResponse.ReviewItem.builder()
                    .userId(x.getUsers().getUserId())
                    .reviewTitle(x.getTitle())
                    .reviewContent(x.getContent())
                    .reviewStar(x.getStar())
                    .insertDate(x.getInsertDate())
                    .build();
        }).toList();

        return ItemReviewResponse.builder()
                .itemId(item.getItemId())
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemName(item.getItemName())
                .itemThumbnail(itemImgList.stream()
                        .filter(y -> y.getItemImgType() == ItemImgType.Y)
                        .map(y -> {
                            return ItemReviewResponse.Thumbnail.builder()
                                    .imgId(y.getItemImgId())
                                    .url(y.getImgUrl())
                                    .build();
                        })
                        .findFirst().orElse(null)
                )
                .reviewList(list)
                .build();

    }

    //회원 - 리뷰 조회
    public UserReviewResponse userReviewFindAll(long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_USER"));

        List<Review> reviewList = reviewRepository.findAllByUsers(user);

        var list = reviewList.stream().map(x -> {
            List<ItemImg> itemImgList = itemImgRepository.findByItem(x.getItem());

            return UserReviewResponse.ReviewItem.builder()
                    .itemId(x.getItem().getItemId())
                    .categoryName(x.getItem().getCategory().getCategoryName())
                    .brandName(x.getItem().getCategory().getBrandName())
                    .itemThumbnail(itemImgList.stream()
                            .filter(y -> y.getItemImgType() == ItemImgType.Y)
                            .map(y -> {
                                return UserReviewResponse.ReviewItem.Thumbnail.builder()
                                        .imgId(y.getItemImgId())
                                        .url(y.getImgUrl())
                                        .build();
                            }).findFirst().orElse(null)
                        )
                    .reviewTitle(x.getTitle())
                    .reviewContent(x.getContent())
                    .reviewStar(x.getStar())
                    .insertDate(x.getInsertDate())
                    .build();
        }).toList();

        return UserReviewResponse.builder()
                        .userId(userId)
                        .reviewItemList(list)
                        .build();
    }

    //리뷰 상세 조회
    public ReviewResponse reviewDetailFind(long reviewId){

        var review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_REVIEW"));

        List<ReviewImg> img = reviewImgRepository.findByReview(review);

        var imgList = img.stream()
                .map(x -> {
                    return ReviewResponse.ReviewImg.builder()
                            .imgId(x.getReviewImgId())
                            .url(x.getImgUrl())
                            .build();
                })
                .toList();

        var item = review.getItem();

        List<ItemImg> itemImgList = itemImgRepository.findByItem(item);

        return ReviewResponse.builder()
                .itemId(item.getItemId())
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemName(item.getItemName())
                .thumbnail(itemImgList.stream()
                        .filter(y -> y.getItemImgType() == ItemImgType.Y)
                        .map(y -> {
                            return ReviewResponse.Thumbnail.builder()
                                    .imgId(y.getItemImgId())
                                    .url(y.getImgUrl())
                                    .build();
                        })
                        .findFirst().orElse(null)
                )
                .userId(review.getUsers().getUserId())
                .reviewTitle(review.getTitle())
                .reviewContent(review.getContent())
                .reviewStar(review.getStar())
                .reviewImgResponses(imgList)
                .insertDate(review.getInsertDate())
                .build();
    }

    //리뷰 등록
    public long create(ReviewRequest reviewRequest){
        var review = reviewRequest.toEntity();
        var result = reviewRepository.save(review);

        //reviewImg
        List<ReviewImg> reviewImgList = reviewRequest
                .reviewImgRequestList()
                .stream()
                .map(x -> {
                    return ReviewImg.builder()
                            .review(review)
                            .imgUrl(x)
                            .insertDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());

        reviewImgRepository.saveAll(reviewImgList);
        return result.getReviewId();
    }

    //리뷰 수정
    public void update(long reviewId, ReviewUpdateRequest reviewUpdateRequest){

        //review
        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_REVIEW"));

        reviewRepository.save(review.editReview(reviewUpdateRequest));

        //reviewImg
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReview(review);

        if(reviewImgList.isEmpty()){
            throw new RuntimeException("NOT_FOUND_REVIEW_IMG");
        }

        //수정할 이미지 개수가 기존과 다르면?
        reviewImgRepository.deleteAll(reviewImgList);

        List<ReviewImg> reviewImgUpdateList = reviewUpdateRequest
                .reviewImgUpdateRequest()
                .stream()
                .map(x -> {
                    return ReviewImg.builder()
                            .review(review)
                            .imgUrl(x)
                            .insertDate(LocalDateTime.now())
                            .updateDate(LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());

        reviewImgRepository.saveAll(reviewImgUpdateList);
    }

    //리뷰 삭제
    public void delete(long reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
