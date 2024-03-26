package com.project.shop.item.repository;


import com.project.shop.item.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

}

