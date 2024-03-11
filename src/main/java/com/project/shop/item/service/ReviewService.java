package com.project.shop.item.service;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.*;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewImgRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.member.domain.Member;
import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.repository.MemberRepository;
import com.project.shop.member.repository.PointRepository;
import com.project.shop.order.domain.Order;
import com.project.shop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final OrderRepository orderImgRepository;
    private final PointRepository pointRepository;

    //상품 - 리뷰 조회
    public ItemReviewResponse findAllByItem(long itemId){

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUNT_ITEM"));

        List<Review> reviewList = reviewRepository.findAllByItem(item);
        List<ItemImg> itemImgList = item.getItemImgList();

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
//                        .findFirst().orElse(null)
                                .findAny().orElse(null)
                )

                .reviewList(list)
                .build();

    }

    //회원 - 리뷰 조회
    public UserReviewResponse findAllByMember(String loginId){

        Member member = findLoginMember(loginId);

        List<Review> reviewList = reviewRepository.findAllByMember(member);

        var list = reviewList.stream().map(review -> {
            List<ItemImg> itemImgList = review.getItem().getItemImgList();

            return UserReviewResponse.ReviewItem.builder()
                    .itemId(review.getItem().getItemId())
                    .categoryName(review.getItem().getCategory().getCategoryName())
                    .itemName(review.getItem().getItemName())
                    .brandName(review.getItem().getCategory().getBrandName())
                    .itemThumbnail(itemImgList.stream()
                            .filter(y -> y.getItemImgType() == ItemImgType.Y)
                            .map(y -> {
                                return UserReviewResponse.ReviewItem.Thumbnail.builder()
                                        .imgId(y.getItemImgId())
                                        .url(y.getImgUrl())
                                        .build();
                            }).findFirst().orElse(null)
                    )
                    .reviewTitle(review.getTitle())
                    .reviewContent(review.getContent())
                    .reviewStar(review.getStar())
                    .insertDate(review.getInsertDate())
                    .build();
        }).toList();

        return UserReviewResponse.builder()
                .userId(member.getUserId())
                .reviewItemList(list)
                .build();
    }

    //리뷰 상세 조회
    public ReviewResponse detailFind(long reviewId){

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

        List<ItemImg> itemImgList = item.getItemImgList();

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
    @Transactional
    public long create(String loginId, ReviewRequest reviewRequest){

        Member member = findLoginMember(loginId);
        Item item = itemRepository.findById(reviewRequest.itemId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

        Order order = orderImgRepository.findById(reviewRequest.orderId())
                .orElseThrow(() -> new RuntimeException("NOT_EXIST_ORDER"));

        if( ! member.equals(order.getMember()) )
            throw new RuntimeException("NOT_EQUAL_ORDER_MEMBER");

        var review = reviewRequest.toEntity(member,item);
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

        //point
        int savePoint = (int) (order.getPrice() * 0.05);
        Point point = Point.builder()
                .member(member)
                .point(savePoint)
                .deadlineDate(LocalDate.now().plusYears(1))
                .pointType(PointType.적립)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        pointRepository.save(point);

        return result.getReviewId();
    }

    //리뷰 수정
    @Transactional
    public void update(String loginId, long reviewId, ReviewUpdateRequest reviewUpdateRequest){

        Member member = findLoginMember(loginId);

        //review
        Review review = reviewFindById(reviewId);
        equalLoginMemberCheck(member,review);

        reviewRepository.save(review.editReview(reviewUpdateRequest));

        //reviewImg
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReview(review);

        if(!reviewImgList.isEmpty()){
            reviewImgRepository.deleteAll(reviewImgList);
        }

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
    @Transactional
    public void delete(String loginId, long reviewId){
        Member member = findLoginMember(loginId);
        Review review = reviewFindById(reviewId);
        equalLoginMemberCheck(member,review);

        reviewRepository.deleteById(reviewId);
        reviewImgRepository.deleteByReview(review);
    }

    //로그인 member 확인
    private Member findLoginMember(String loginId){

        return memberRepository.findByLoginId(loginId)
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
            throw new RuntimeException("NOT_EQUAL_REVIEW_MEMBER");
    }
}
