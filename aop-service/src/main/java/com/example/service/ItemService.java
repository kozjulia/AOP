package com.example.service;

import com.example.dto.ItemDto;
import com.example.dto.NewItemDto;
import com.example.dto.UpdateItemDto;
import org.springframework.data.domain.Page;

public interface ItemService {

    ItemDto getItemById(Long itemId);

    Page<ItemDto> getAllItems(Integer from, Integer size);

    ItemDto saveItem(NewItemDto newItemDto);

    void updateItem(Long itemId, UpdateItemDto updateItemDto);

    void deleteItemById(Long itemId);

}