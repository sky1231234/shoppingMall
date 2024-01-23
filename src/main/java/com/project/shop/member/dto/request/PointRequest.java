package com.project.shop.member.dto.request;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PointRequest(
        @NotNull long id,
        @NotNull int point,
        @NotBlank LocalDateTime deadlineDate

        ) {

        public Point toEntity(Member member,PointType pointType){
                return Point.builder()
                        .member(member)
                        .point(this.point())
                        .deadlineDate(this.deadlineDate())
                        .pointType(pointType)
                        .insertDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();
        }
}
