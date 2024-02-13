package com.project.shop.global.common;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class CronTable {
    private final PointRepository pointRepository;

    @Value("${schedule.use}")
    private boolean useSchedule;

    @Scheduled(cron = "${schedule.cron}")
    public void schedule() {

            if (useSchedule) {
                List<Point> pointList = pointRepository.findAll().stream()
                                            .filter(x -> x.getPointType() == PointType.적립)
                                            .filter(x -> Objects.equals(x.getDeadlineDate(), LocalDate.now().minusDays(1)))
                                            .map(Point::expirePoint
                                            ).toList();

                pointRepository.saveAll(pointList);
            }

    }
}