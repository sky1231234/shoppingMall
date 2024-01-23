package com.project.shop.member.repository;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findAllByMember(Member member);

    //사용 가능한 포인트
    @Query(nativeQuery = true , value =
            "SELECT COALESCE(sum(point), 0) as point " +
                    "FROM point " +
                    "where now() <= deadline_date " +
                    "AND state = '적립' " +
                    "AND user_id = :userId"
    )
    int findSumPoint(@Param(value="userId") long userId);

//    DATE_ADD (now(), INTERVAL +1 MONTH)
    //소멸 예정 포인트
    @Query(nativeQuery = true , value =
    "SELECT COALESCE(sum(point), 0) as point " +
            "FROM point " +
            "WHERE state = '적립' " +
            "AND deadline_date BETWEEN NOW() AND TIMESTAMPADD(MONTH, 1, NOW()) " +
            "AND user_id = :userId"
    )
    int findDisappearPoint(@Param(value="userId") long userId);
}
