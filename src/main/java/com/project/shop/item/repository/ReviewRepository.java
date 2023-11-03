package com.project.shop.item.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> detailReview(int reviewId);

}
