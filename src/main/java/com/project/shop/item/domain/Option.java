package com.project.shop.item.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "option")
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long optionId;     //옵션번호

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Builder
    public Option(Item item, String color, String size, LocalDateTime dateTime) {
        this.item = item;
        this.color = color;
        this.size = size;
        this.insertDate = dateTime;
        this.updateDate = dateTime;
    }
}
