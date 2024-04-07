package com.example.service;

import com.example.dto.TrackTimeDto;
import java.util.List;

public interface TrackTimeService {

    TrackTimeDto getTrackTimeById(Long trackTimeId);

    List<TrackTimeDto> getAllTrackTimes(Integer from, Integer size, String name, String className, String methodName);

    Long sumAllTrackTimes(String name, String className, String methodName);

    Double avgAllTrackTimes(String name, String className, String methodName);

}