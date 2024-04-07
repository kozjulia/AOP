package com.example.repository;

import com.example.model.TrackTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackTimeRepository extends CrudRepository<TrackTime, Long> {

}