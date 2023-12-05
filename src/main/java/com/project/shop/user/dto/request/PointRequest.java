package com.project.shop.user.dto.request;

import com.project.shop.user.domain.Point;
import com.project.shop.user.domain.PointType;
import com.project.shop.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record PointRequest(
        @NotNull long userId,
        @NotNull int point,
        @NotBlank LocalDateTime deadlineDate,
        @NotBlank PointType state

        ) {

        public Point toEntity(User user){
                return Point.builder()
                        .user(user)
                        .point(this.point())
                        .deadlineDate(this.deadlineDate())
                        .pointType(this.state())
                        .build();
        }
}
