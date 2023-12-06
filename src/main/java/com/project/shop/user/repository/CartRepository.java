package com.project.shop.user.repository;


import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.user.domain.Cart;
import com.project.shop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUsers(User user);
    Optional<Cart> findByUsersAndAndItemAndOptionId(User user, Item item, long optionId);
}
