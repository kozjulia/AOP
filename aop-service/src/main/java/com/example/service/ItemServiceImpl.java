package com.example.service;

import com.example.dto.ItemDto;
import com.example.dto.NewItemDto;
import com.example.dto.UpdateItemDto;
import com.example.exception.NotFoundException;
import com.example.mapper.ItemMapper;
import com.example.mapper.ItemServiceMapper;
import com.example.model.Item;
import com.example.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemServiceMapper itemServiceMapper;

    @Transactional(readOnly = true)
    @Override
    public ItemDto getItemById(Long itemId) {
        return itemMapper.toItemDto(returnItem(itemId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ItemDto> getAllItems(Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size);
        Page<Item> itemsPage = itemRepository.findAll(page);
        return itemsPage.map(itemMapper::toItemDto);
    }

    @Override
    public ItemDto saveItem(NewItemDto newItemDto) {
        Item item = itemRepository.save(itemServiceMapper.toItemFromNewItemDto(newItemDto));
        return itemMapper.toItemDto(item);
    }

    @Override
    public void updateItem(Long itemId, UpdateItemDto updateItemDto) {
        Item item = returnItem(itemId);
        item = itemServiceMapper.toItemFromUpdateItemDto(item, updateItemDto);
        itemRepository.save(item);
    }

    @Override
    public void deleteItemById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    private Item returnItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Элемент с id = " + itemId + " не найден."));
    }

}