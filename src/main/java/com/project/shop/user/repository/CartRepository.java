package com.project.shop.user.repository;


import com.project.shop.item.domain.Category;
import com.project.shop.user.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserId(Long userId);
    Optional<Cart> findByUserIdAndAndItemIdAndOptionNum(long userId, long itemId, long optionNum);
}
