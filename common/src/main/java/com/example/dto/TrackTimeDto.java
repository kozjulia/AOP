package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Время выполнения")
public class TrackTimeDto {

    @Schema(description = "Идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Наименование аннотации, вызвавшая запись времени выполнения", example = "TrackTime")
    private String name;

    @Schema(description = "Наименование класса", example = "ItemController")
    private String className;

    @Schema(description = "Наименование метода", example = "getItemById")
    private String methodName;

    @Schema(description = "Время выполнения", example = "1415")
    private Long totalTime;

}