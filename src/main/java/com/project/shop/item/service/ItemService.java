package com.project.shop.item.service;

import com.project.shop.item.dto.request.ItemRequest;

public interface ItemService {
    long create(String loginId, ItemRequest itemRequest);
}
