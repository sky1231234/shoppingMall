package com.project.shop.user.repository;

import com.project.shop.item.domain.Category;
import com.project.shop.user.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findAllByUserId(long userId);

    //사용가능한 포인트
    @Query(
            "SELECT COALESCE(sum(point), 0) as point" +
                    "FROM point" +
                    "where now() <= deadlineDate" +
                    "AND state = '적립'" +
                    "AND userId = :userId"
    )
    int findSumPoint(@Param(value="userId") long userId);

    //소멸 예정 포인트
    @Query(
    "SELECT COALESCE(sum(point), 0) as point " +
            "FROM point" +
            "WHERE deadlineDate BETWEEN NOW() AND DATE_ADD(NOW(),INTERVAL +1 MONTH)" +
            "AND state = '적립'" +
            "AND userId = :userId"
    )
    int findDisappearPoint(@Param(value="userId") long userId);
}
