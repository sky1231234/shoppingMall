package com.project.shop.item.service;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.*;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewImgRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
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
                    .userId(x.getMember().getUserId())
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
    public UserReviewResponse userReviewFindAll(UserDto userDto){

        Member member = findLoginMember(userDto);

        List<Review> reviewList = reviewRepository.findAllByMember(member);

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
                        .userId(member.getUserId())
                        .reviewItemList(list)
                        .build();
    }

    //리뷰 상세 조회
    public ReviewResponse reviewDetailFind(long reviewId){

        Review review = reviewFindById(reviewId);

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
                .userId(review.getMember().getUserId())
                .reviewTitle(review.getTitle())
                .reviewContent(review.getContent())
                .reviewStar(review.getStar())
                .reviewImgResponses(imgList)
                .insertDate(review.getInsertDate())
                .build();
    }

    //리뷰 등록
    public long create(UserDto userDto, ReviewRequest reviewRequest){

        Member member = findLoginMember(userDto);
        var review = reviewRequest.toEntity(member);
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
    public void update(UserDto userDto, long reviewId, ReviewUpdateRequest reviewUpdateRequest){

        Member member = findLoginMember(userDto);

        //review
        Review review = reviewFindById(reviewId);
        equalLoginMemberCheck(member,review);

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
    public void delete(UserDto userDto, long reviewId){
        Member member = findLoginMember(userDto);
        Review review = reviewFindById(reviewId);
        equalLoginMemberCheck(member,review);

        reviewRepository.deleteById(reviewId);
    }

    //로그인 member 확인
    private Member findLoginMember(UserDto userDto){

        return memberRepository.findByLoginId(userDto.getLoginId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
    }

    //review 확인
    private Review reviewFindById(long reviewId){

        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_REVIEW"));
    }

    //로그인 member와 review member 비교
    private void equalLoginMemberCheck(Member member, Review review){
        if( ! member.equals(review.getMember()) )
            throw new RuntimeException("NOT_EQUAL_review_MEMBER");
    }
}
