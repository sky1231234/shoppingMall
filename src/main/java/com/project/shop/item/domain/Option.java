package com.project.shop.item.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "option")
@Entity
@Getter
@Builder
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionId")
    private long optionId;     //옵션번호

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId")
    private Item item;     //상품번호

    @Column(name = "color", nullable = false)
    private String color;    //색상
    @Column(name = "size", nullable = false)
    private String size;    //사이즈

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //옵션 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //옵션 수정일


}
