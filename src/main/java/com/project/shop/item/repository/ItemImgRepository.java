package com.project.shop.item.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ItemImgType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItem(Item item);
    ItemImg findByItemAndItemImgType(Item item, ItemImgType imgType);
    void deleteByItem(Item item);
}

