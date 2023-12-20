package com.project.shop.order.repository;


import com.project.shop.item.domain.Item;
import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

    Optional<OrderItem> findByItemAndOrder(Item item, Order order);
}
