package com.project.shop.member.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.shop.member.repository.PointRepository;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "point")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long pointId;    //포인트번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;

    @Column(name = "point", nullable = false)
    private int point;   //포인트
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "deadlineDate", nullable = true)
    private LocalDate deadlineDate;    //유효기간
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PointType pointType;   //포인트상태

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //포인트 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //포인트 수정일

    @Builder
    public Point(Member member, int point, LocalDate deadlineDate,
                      PointType pointType, LocalDateTime dateTime) {
        this.member = member;
        this.point = point;
        this.deadlineDate = deadlineDate;
        this.pointType = pointType;
        this.insertDate = dateTime;
        this.updateDate = dateTime;
    }

    public Point expirePoint(){
        this.pointType = PointType.만료;
        this.updateDate = LocalDateTime.now();
        return this;
    }



}