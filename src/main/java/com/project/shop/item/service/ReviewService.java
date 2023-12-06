package com.project.shop.item.service;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.*;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewImgRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.user.domain.User;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //상품 - 리뷰 조회
    public ItemReviewResponse itemReviewFindAll(long itemId){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUNT_ITEM"));

        List<Review> reviewList = reviewRepository.findAllByItem(item);

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
                .itemThumbnail(item.getItemImgList().stream()
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
            return UserReviewResponse.ReviewItem.builder()
                    .itemId(x.getItem().getItemId())
                    .categoryName(x.getItem().getCategory().getCategoryName())
                    .brandName(x.getItem().getCategory().getBrandName())
                    .itemThumbnail(x.getItem().getItemImgList().stream()
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

        return ReviewResponse.builder()
                .itemId(item.getItemId())
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemName(item.getItemName())
                .thumbnail(item.getItemImgList().stream()
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
    public void create(ReviewRequest reviewRequest){
        var review = reviewRequest.toEntity();
        reviewRepository.save(review);

        //reviewImg
        List<ReviewImg> reviewImgList = reviewRequest
                .reviewImgRequestList()
                .stream()
                .map(x -> {
                    var entity = x.toEntity();
                    return entity.updateReview(review);
                })
                .collect(Collectors.toList());

        reviewImgRepository.saveAll(reviewImgList);
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
                    var entity = x.toEntity();
                    return entity.updateReview(review);
                })
                .collect(Collectors.toList());

        reviewImgRepository.saveAll(reviewImgUpdateList);
    }

    //리뷰 삭제
    public void delete(long reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
