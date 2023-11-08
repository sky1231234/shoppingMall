package com.project.shop.item.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "option")
@Entity
@Getter
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionId")
    private long optionId;     //옵션번호
    @Column(name = "itemId", nullable = false)
    private long itemId;     //상품번호
    @Column(name = "color", nullable = false)
    private String color;    //색상
    @Column(name = "size", nullable = false)
    private String size;    //사이즈

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //옵션 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //옵션 수정일


}
