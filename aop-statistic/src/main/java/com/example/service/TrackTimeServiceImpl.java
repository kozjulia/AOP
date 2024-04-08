package com.example.service;

import com.example.dto.TrackTimeDto;
import com.example.exception.NotFoundException;
import com.example.mapper.TrackTimeMapper;
import com.example.model.TrackTime;
import com.example.repository.TrackTimeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackTimeServiceImpl implements TrackTimeService {

    private final TrackTimeRepository trackTimeRepository;
    private final TrackTimeMapper trackTimeMapper;

    public TrackTimeDto getTrackTimeById(Long trackTimeId) {
        return trackTimeMapper.toTrackTimeDto(returnTrackTime(trackTimeId));
    }

    public List<TrackTimeDto> getAllTrackTimes(
            Integer from, Integer size, String name, String className, String methodName) {
        Pageable page = PageRequest.of(from, size);
        List<TrackTime> trackTimes = trackTimeRepository.getAllTrackTimes(name, className, methodName, page);
        return trackTimeMapper.convertTrackTimeListToTrackTimeDtoList(trackTimes);
    }

    public Long sumAllTrackTimes(String name, String className, String methodName) {
        return trackTimeRepository.sumAllTrackTimes(name, className, methodName);
    }

    public Double avgAllTrackTimes(String name, String className, String methodName) {
        return trackTimeRepository.avgAllTrackTimes(name, className, methodName);
    }

    private TrackTime returnTrackTime(Long trackTimeId) {
        return trackTimeRepository.findById(trackTimeId)
                .orElseThrow(() -> new NotFoundException("Статистика с id = " + trackTimeId + " не найдена."));
    }

}