package com.project.shop.item.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.user.domain.Cart;
import com.project.shop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


}
