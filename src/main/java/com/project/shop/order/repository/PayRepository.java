package com.project.shop.order.repository;


import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay, Long> {

    Pay findByOrder(Order order);
}
