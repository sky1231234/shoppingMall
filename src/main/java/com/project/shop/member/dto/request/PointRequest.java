package com.project.shop.member.dto.request;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PointRequest(
        @NotNull long userId,
        @NotNull int point,
        @NotBlank LocalDateTime deadlineDate,
        @NotBlank PointType state

        ) {

        public Point toEntity(Member member){
                return Point.builder()
                        .users(member)
                        .point(this.point())
                        .deadlineDate(this.deadlineDate())
                        .pointType(this.state())
                        .build();
        }
}
