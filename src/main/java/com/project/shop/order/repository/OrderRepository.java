package com.project.shop.order.repository;


import com.project.shop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<User, Long> {

}
