package com.project.shop.user.repository;

import com.project.shop.user.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findAllByUserId(long userId);
}
