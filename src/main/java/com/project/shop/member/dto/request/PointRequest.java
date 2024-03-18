package com.project.shop.member.dto.request;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.domain.Member;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PointRequest(
        @NotNull long id,
        @NotNull int point,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @NotNull LocalDate deadlineDate

        ) {

        public Point toEntity(Member member,PointType pointType){
                return Point.builder()
                        .member(member)
                        .point(this.point())
                        .deadlineDate(this.deadlineDate())
                        .pointType(pointType)
                        .dateTime(LocalDateTime.now())
                        .build();
        }
}
