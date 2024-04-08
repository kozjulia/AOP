package com.example.model;

import com.example.mapper.TrackTimeMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;

@Slf4j
public class TrackTimeListener {

    private final TrackTimeMapper trackTimeMapper = Mappers.getMapper(TrackTimeMapper.class);

    @PostLoad
    private void afterLoad(TrackTime trackTime) {
        log.info("Получено время выполнения, id = {}: {}.", trackTime.getId(), trackTimeMapper.toTrackTimeDto(trackTime));
    }

    @PostPersist
    private void afterPersist(TrackTime trackTime) {
        log.info("Добавлено время выполнения: {}.", trackTimeMapper.toTrackTimeDto(trackTime));
    }

}