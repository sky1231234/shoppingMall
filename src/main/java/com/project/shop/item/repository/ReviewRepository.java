package com.project.shop.item.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    //리뷰 상세 조회
    Review detailReview(int reviewId);


    //상품 - 리뷰 조회
    //상품 id만 제공하면 되니까 item?
    Item itemReview(int itemId);
}
