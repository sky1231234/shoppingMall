package com.project.shop.order.repository;


import com.project.shop.order.domain.Order;
import com.project.shop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUsers(User user);

}
