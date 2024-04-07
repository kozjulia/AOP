package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Новое время выполнения")
public class NewTrackTimeDto {

    @Schema(description = "Наименование время выполнения", example = "TrackTime ")
    private String name;

    @Schema(description = "Наименование класса", example = "ItemController")
    private String className;

    @Schema(description = "Наименование метода", example = "getItemById")
    private String methodName;

    @Schema(description = "Время выполнения", example = "4552")
    private Long totalTime;

}