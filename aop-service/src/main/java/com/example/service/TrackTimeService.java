package com.example.service;

import com.example.dto.NewTrackTimeDto;
import com.example.model.TrackTime;

public interface TrackTimeService {

    TrackTime saveTrackTime(NewTrackTimeDto trackTimeDto);

}