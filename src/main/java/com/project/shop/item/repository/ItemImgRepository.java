package com.project.shop.item.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.dto.response.ItemListResponse;
import com.project.shop.item.dto.response.ItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItem(Item item);
    List<ItemImg> findByItemAndItemImgType(Item item, ItemImgType imgType);
}

