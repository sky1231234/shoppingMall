package com.project.shop.order.repository;


import com.project.shop.order.domain.Order;
import com.project.shop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMember(Member member);

}
