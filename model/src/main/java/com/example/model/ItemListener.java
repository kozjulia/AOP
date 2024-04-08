package com.example.model;

import com.example.mapper.ItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;

@Slf4j
public class ItemListener {

    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    @PostLoad
    private void afterLoad(Item item) {
        log.info("Получен элемент, id = {}: {}.", item.getId(), itemMapper.toItemDto(item));
    }

    @PostPersist
    private void afterPersist(Item item) {
        log.info("Добавлен новый элемент: {}.", itemMapper.toItemDto(item));
    }

    @PostRemove
    private void afterRemove(Item item) {
        log.info("Удален элемент с id = {}.", item.getId());
    }

}