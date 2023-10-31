package com.project.shop.user.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "point")
@Entity
public class Point {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pointId")
    private int pointId;    //포인트번호
    @Column(name = "userId", nullable = false)
    private int userId;      //고객번호
    @Column(name = "point", nullable = false)
    private String point;   //포인트
    @Column(name = "deadlineDate", nullable = false)
    private LocalDateTime deadlineDate;    //유효기간
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PointType pointType;   //포인트상태

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //포인트 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //포인트 수정일
}