package com.project.shop.item.service;

import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;

public interface ItemService {
    long create(String loginId, ItemRequest itemRequest);
    void update(String loginId, long itemId, ItemUpdateRequest itemUpdateRequest);
}
