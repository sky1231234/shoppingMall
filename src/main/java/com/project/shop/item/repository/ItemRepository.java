package com.project.shop.item.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
