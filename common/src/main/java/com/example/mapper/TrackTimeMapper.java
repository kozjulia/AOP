package com.example.mapper;

import com.example.dto.TrackTimeDto;
import com.example.model.TrackTime;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackTimeMapper {

    TrackTimeDto toTrackTimeDto(TrackTime trackTime);

    List<TrackTimeDto> convertTrackTimeListToTrackTimeDtoList(List<TrackTime> list);

}