package com.project.shop.item.repository;


import com.project.shop.item.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    //query어노테이션사용해서 메인이미지 가져오기
    ItemImg findByItemIdAndMainImg(long itemId, String mainImg);
    List<ItemImg> findByItemId(long itemId);

}

