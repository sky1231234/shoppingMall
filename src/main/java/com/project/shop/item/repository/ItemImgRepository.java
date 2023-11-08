package com.project.shop.item.repository;


import com.project.shop.item.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImgMapping> findByItemIdAndMainImg(long itemId, String mainImg);

}

