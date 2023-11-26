package com.project.shop.user.dto.response;

import com.project.shop.user.domain.Address;
import com.project.shop.user.domain.AddressType;
import com.project.shop.user.domain.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointResponse {

    private int sumPoint; //총 포인트
    private int disappearPoint; //소멸 예정 포인트
    private int point; //포인트
    private String deadlineDate; //유효기간
    private String state; //포인트 상태
    private AddressType date; //적립/사용일

    public static PointResponse fromEntity(Point point) {

        return PointResponse.builder()
                .sumPoint(point)
                .disappearPoint(point)
                .point(point.getPoint())
                .deadlineDate(point.getDeadlineDate())
                .state(point.getPointType())
                .date(point.getInsertDate())
                .build();
    }
}