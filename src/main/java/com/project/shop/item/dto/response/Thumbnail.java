package com.project.shop.item.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Thumbnail {
    private long itemImgId;
    private String imgUrl;
}
