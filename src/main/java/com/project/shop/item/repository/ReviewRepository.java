package com.project.shop.item.repository;


import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //상품 - 리뷰 조회
    //상품 id만 제공하면 되니까 item?
//    List<Review> findByItemIdAndMainImg(long itemId,String mainImg);
}
