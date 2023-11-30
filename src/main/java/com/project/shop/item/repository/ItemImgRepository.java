package com.project.shop.item.repository;


import com.project.shop.item.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemId(long itemId);

}

