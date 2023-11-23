package com.project.shop.item.repository;


import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {
//    ItemImg findByItemIdAndMainImg(long itemId, String mainImg);
//    List<ItemImg> findByItemId(long itemId);

}

