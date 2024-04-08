package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Элемент")
public class ItemDto {

    @Schema(description = "Идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Наименование элемента", example = "Борщ ")
    private String name;

    @Schema(description = "Полное описание блюда", example = "Борщ с хлебом и салом")
    private String description;

    @Schema(description = "Цена блюда", example = "350")
    private Integer price;

}