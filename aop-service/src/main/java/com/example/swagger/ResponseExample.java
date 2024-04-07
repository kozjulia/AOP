package com.example.swagger;

public class ResponseExample {

    public static final String ITEM_ERROR_400_EXAMPLE = """
            {
               "status": "BAD_REQUEST",
               "reason": "Incorrectly made request.",
               "message": "Field: name. Error: Ошибка! Наименование элемента не может быть пустым. Value:  Field: name. Error: Ошибка! Наименование элемента может содержать минимум 5, максимум 100 символов. Value:  ",
               "timestamp": "2024-03-31 18:32:22"}""";

    public static final String ITEM_ERROR_404_EXAMPLE = """
            {
               "status": "NOT_FOUND",
               "reason": "The required object was not found.",
               "message": "Элемент с id = 9 не найден.",
               "timestamp": "2024-03-31 18:33:49"}""";

}