package com.project.shop.order.repository;


import com.project.shop.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


    List<OrderItem> findByOrderId(long orderId);

    OrderItem findByItemIdAndOrderId(long itemId, long orderId);
}
