package com.example.repository;

import com.example.model.TrackTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackTimeRepository extends JpaRepository<TrackTime, Long> {

    @Query("""
            select tt from TrackTime tt 
            where (:name is null or tt.name = :name) 
            and (:className is null or tt.className = :className) 
            and (:methodName is null or tt.methodName = :methodName) 
            """)
    List<TrackTime> getAllTrackTimes(
            @Param("name") String name,
            @Param("className") String className,
            @Param("methodName") String methodName,
            Pageable page);

    @Query("""
            select sum(tt.totalTime) from TrackTime tt 
            where (:name is null or tt.name = :name) 
            and (:className is null or tt.className = :className) 
            and (:methodName is null or tt.methodName = :methodName) 
            """)
    Long sumAllTrackTimes(
            @Param("name") String name,
            @Param("className") String className,
            @Param("methodName") String methodName);

    @Query("""
            select avg(tt.totalTime) from TrackTime tt 
            where (:name is null or tt.name = :name) 
            and (:className is null or tt.className = :className) 
            and (:methodName is null or tt.methodName = :methodName) 
            """)
    Double avgAllTrackTimes(
            @Param("name") String name,
            @Param("className") String className,
            @Param("methodName") String methodName);

}