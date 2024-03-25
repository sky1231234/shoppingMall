package com.project.shop.item.domain;

import com.project.shop.item.dto.request.ImgRequest;
import com.project.shop.item.dto.request.ImgUpdateRequest;
import com.project.shop.item.dto.response.ItemImgResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "itemImg")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long itemImgId;     //상품 이미지 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;     //상품

    @Column(name = "imgUrl", nullable = false)
    private String imgUrl;     //이미지 경로

    @Column(name = "mainImg", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemImgType itemImgType;    //대표 이미지 여부

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 이미지 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 이미지 수정일

    @Builder
    public ItemImg(Item item, String imgUrl, ItemImgType itemImgType, LocalDateTime dateTime) {
        this.item = item;
        this.imgUrl = imgUrl;
        this.itemImgType = itemImgType;
        this.insertDate = dateTime;
        this.updateDate = dateTime;
    }

    public List<ItemImg> toItemImgList(List<ImgRequest> itemImgRequestList, Item item){
        return itemImgRequestList
                .stream()
                .map(ImgRequest -> ImgRequest.toEntity(item))
                .toList();
    }

    public List<ItemImg> toItemImgListForUpdate(List<ImgUpdateRequest> imgUpdateRequestList, Item item){
        return imgUpdateRequestList
                .stream()
                .map(ImgUpdateRequest -> ImgUpdateRequest.toEntity(item))
                .toList();

    }
    public ItemImgResponse getMainImgByItem(Item item){

        return getItemImgListByItemType(item,ItemImgType.Y)
                .stream().findFirst()
                .orElseThrow(()->new RuntimeException("NOT_FOUND_IMG"));
    }

    public List<ItemImgResponse> getItemImgListByItemType(Item item, ItemImgType itemImgType){

        return item.getItemImgList().stream()
                .filter(itemImg -> itemImg.getItemImgType() == itemImgType)
                .map(ItemImgResponse::of)
                .toList();

    }
}
