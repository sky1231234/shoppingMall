package com.project.shop.member.dto.request;

import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PointUpdateRequest(
        @NotNull int point,
        @NotBlank LocalDateTime deadlineDate,
        @NotBlank PointType state
    ){

    public Point toEntity(){
        return Point.builder()
                .point(this.point())
                .deadlineDate(this.deadlineDate())
                .pointType(this.state())
                .build();
    }
}
