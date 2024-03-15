package com.project.shop.ordersheet.repository;

import com.project.shop.ordersheet.domain.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {
}
