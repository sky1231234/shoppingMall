package com.project.shop.user.dto.request;

import com.project.shop.user.domain.Address;
import com.project.shop.user.domain.AddressType;
import com.project.shop.user.domain.Point;
import com.project.shop.user.domain.PointType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record PointUpdateRequest(
        @NotNull int point,
        @NotBlank LocalDateTime deadlineDate,
        @NotBlank PointType state
    ){

    public Point toEntity(){
        return Point.builder()
                .point(this.getPoint())
                .deadlineDate(this.getDeadlineDate())
                .pointType(this.getState())
                .build();
    }
}
