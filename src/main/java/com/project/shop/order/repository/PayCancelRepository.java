package com.project.shop.order.repository;


import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.PayCancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayCancelRepository extends JpaRepository<PayCancel, Long> {

    PayCancel findByOrder(Order order);
}
