package com.project.shop.member.domain;

import com.project.shop.member.dto.request.PointUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "point")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long pointId;    //포인트번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member users;

    @Column(name = "point", nullable = false)
    private int point;   //포인트
    @Column(name = "deadlineDate", nullable = false)
    private LocalDateTime deadlineDate;    //유효기간
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PointType pointType;   //포인트상태

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //포인트 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //포인트 수정일

    public Point editPoint(PointUpdateRequest pointUpdateRequest){
        this.point = pointUpdateRequest.point();
        this.deadlineDate = pointUpdateRequest.deadlineDate();
        this.pointType = pointUpdateRequest.state();
        this.updateDate = LocalDateTime.now();
        return this;
    }

}