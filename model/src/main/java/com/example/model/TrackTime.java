package com.example.model;

import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "track_times")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TrackTime {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "track_time_seq")
    @SequenceGenerator(name = "track_time_seq", sequenceName = "SEQ_TRACK_TIME", allocationSize = 10)
    @Column(name = "track_times_id", nullable = false)
    private Long id; // Идентификатор

    @Column(name = "track_times_name", nullable = false)
    private String name; // Наименование

    @Column(name = "track_times_class_name", nullable = false)
    private String className; // Наименование класса

    @Column(name = "track_times_method_name", nullable = false)
    private String methodName; // Наименование метода

    @Column(name = "track_times_total_time", nullable = false)
    private Long totalTime; // Время выполнения

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackTime trackTime = (TrackTime) o;
        return Objects.equals(name, trackTime.name) && Objects.equals(className, trackTime.className) &&
                Objects.equals(methodName, trackTime.methodName) && Objects.equals(totalTime, trackTime.totalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, className, methodName, totalTime);
    }

}