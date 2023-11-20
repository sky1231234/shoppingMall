package com.project.shop.item.domain;

import com.project.shop.item.dto.response.ItemImgResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "itemImg")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemImgId")
    private long itemImgId;     //상품 이미지 번호

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId")
    private Item item;     //상품

    @Column(name = "imgUrl", nullable = false)
    private String imgUrl;     //이미지 경로
    @Column(name = "imgName", nullable = false)
    private String imgName;    //이미지명

    @Column(name = "mainImg", nullable = false)
    @Enumerated(EnumType.STRING)
    private  ItemImgType itemImgType;    //대표이미지 여부

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품이미지 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품이미지수정일

    public List<ItemImgResponse> updateItem(Item item){
        this.item = item;
        return this;
    }
}
