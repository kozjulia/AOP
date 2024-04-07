package com.example.service;

import com.example.dto.NewTrackTimeDto;
import com.example.mapper.TrackTimeServiceMapper;
import com.example.model.TrackTime;
import com.example.repository.TrackTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackTimeServiceImpl implements TrackTimeService {

    private final TrackTimeRepository trackTimeRepository;
    private final TrackTimeServiceMapper trackTimeServiceMapper;

    @Override
    public TrackTime saveTrackTime(NewTrackTimeDto newTrackTimeDto) {
        return trackTimeRepository.save(trackTimeServiceMapper.toTrackTimeFromNewTrackTimeDto(newTrackTimeDto));
    }

}