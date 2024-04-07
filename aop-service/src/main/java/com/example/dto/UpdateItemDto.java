package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "Обновлённый элемент")
public class UpdateItemDto {

    @Positive(message = "Ошибка! Идентификатор элемента должен быть положительным.")
    @Schema(description = "Идентификатор", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Ошибка! Наименование элемента не может быть пустым.")
    @Size(min = 5, max = 100, message = "Ошибка! Наименование элемента может содержать минимум 5, максимум 100 символов.")
    @Schema(description = "Наименование элемента", example = "Борщ ")
    private String name;

    @NotBlank(message = "Ошибка! Полное описание блюда не может быть пустым.")
    @Size(min = 5, max = 200, message = "Ошибка! Полное описание блюда может содержать минимум 5, максимум 200 символов.")
    @Schema(description = "Полное описание блюда", example = "Борщ с хлебом и салом")
    private String description;

    @NotNull(message = "Ошибка! Цена блюда не может быть пустой.")
    @PositiveOrZero(message = "Ошибка! Цена блюда не может быть отрицательной.")
    @Schema(description = "Цена", example = "550")
    private Integer price;

}