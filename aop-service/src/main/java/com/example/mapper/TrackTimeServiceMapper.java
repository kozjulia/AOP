package com.example.mapper;

import com.example.dto.NewTrackTimeDto;
import com.example.model.TrackTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrackTimeServiceMapper {

    @Mapping(target = "id", ignore = true)
    TrackTime toTrackTimeFromNewTrackTimeDto(NewTrackTimeDto newTrackTimeDto);

}