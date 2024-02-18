package com.project.shop.global.config.Mapper;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.dto.response.ItemListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(source = "item.category.categoryName", target = "categoryName")
    @Mapping(source = "item.category.brandName", target = "brandName")
    @Mapping(source = "mainItemImg.itemImgId", target = "thumbnail.itemImgId")
    @Mapping(source = "mainItemImg.imgUrl", target = "thumbnail.imgUrl")
    ItemListResponse toItemListResponse(Item item, ItemImg mainItemImg);
}
