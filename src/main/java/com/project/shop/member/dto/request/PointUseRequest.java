package com.project.shop.member.dto.request;

import com.project.shop.member.domain.Member;
import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PointUseRequest(
        @NotNull long id,
        @NotNull int point

        ) {

        public Point toEntity(Member member,PointType pointType){
                return Point.builder()
                        .member(member)
                        .point(this.point() * -1)
                        .pointType(pointType)
                        .deadlineDate(LocalDate.now())
                        .insertDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();
        }
}
