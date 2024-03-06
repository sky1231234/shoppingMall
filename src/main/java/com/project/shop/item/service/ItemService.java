package com.project.shop.item.service;

import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.dto.response.ItemListResponse;
import com.project.shop.item.dto.response.ItemResponse;

import java.util.List;

public interface ItemService {
    List<ItemListResponse> findAll();
    ItemResponse detailFind(long itemId);
    long create(String loginId, ItemRequest itemRequest);
    void update(String loginId, long itemId, ItemUpdateRequest itemUpdateRequest);
    void delete(String loginId, long itemId);
}
