package com.example.controller;

import com.example.dto.TrackTimeDto;
import com.example.exception.ApiError;
import com.example.service.TrackTimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import static com.example.swagger.ResponseExample.TRACK_TIME_ERROR_404_EXAMPLE;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Статистика", description = "Взаимодействие со статистикой")
public class TrackTimeController {

    private final TrackTimeService trackTimeService;

    @GetMapping("/{trackTimeId}")
    @Operation(
            summary = "Получить статистику",
            description = "Позволяет возвратить информацию о статистике по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrackTimeDto.class))}),
            @ApiResponse(responseCode = "404", description = "The required object was not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(value = TRACK_TIME_ERROR_404_EXAMPLE)})})})
    /**
     * Возвращение информации о статистики по id.
     */
    public ResponseEntity<TrackTimeDto> getTrackTimeById(
            @PathVariable @Parameter(description = "Идентификатор статистики", required = true) Long trackTimeId) {
        TrackTimeDto trackTimeDto = trackTimeService.getTrackTimeById(trackTimeId);
        return ResponseEntity.ok().body(trackTimeDto);
    }

    @GetMapping
    @Operation(
            summary = "Получить всю статистику",
            description = "Позволяет возвратить информацию обо всей статистике по имени аннотации, классу и методу, вызвавшим аннотацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = TrackTimeDto.class)))})})
    /**
     * Возвращение информации обо всей статистике по имени аннотации, классу и методу, вызвавшим аннотацию.
     */
    public ResponseEntity<List<TrackTimeDto>> getAllItems(
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
            @Parameter(description = "номер страницы") Integer from,
            @Positive @Max(500) @RequestParam(name = "size", defaultValue = "10")
            @Parameter(description = "размер страницы") Integer size,
            @RequestParam(name = "name", required = false)
            @Parameter(description = "наименование аннотации") String name,
            @RequestParam(name = "className", required = false)
            @Parameter(description = "имя класса") String className,
            @RequestParam(name = "methodName", required = false)
            @Parameter(description = "имя метода") String methodName) {
        List<TrackTimeDto> trackTimeDtos = trackTimeService.getAllTrackTimes(from, size, name, className, methodName);
        log.info("Получен список статистики, from = {}, size = {}, name = {}, className = {}. methodName = {}, " +
                "количество = {}.", from, size, name, className, methodName, trackTimeDtos.stream().count());
        return ResponseEntity.ok().body(trackTimeDtos);
    }

    @GetMapping("/sum")
    @Operation(
            summary = "Получить сумму всей статистики",
            description = "Позволяет возвратить информацию о сумме всей статистики по имени аннотации, классу и методу, вызвавшим аннотацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = TrackTimeDto.class)))})})
    /**
     * Возвращение информации о сумме всей статистики по имени аннотации, классу и методу, вызвавшим аннотацию.
     */
    public ResponseEntity<Long> sumAllTrackTimes(
            @RequestParam(name = "name", required = false)
            @Parameter(description = "наименование аннотации") String name,
            @RequestParam(name = "className", required = false)
            @Parameter(description = "имя класса") String className,
            @RequestParam(name = "methodName", required = false)
            @Parameter(description = "имя метода") String methodName) {
        Long sumAllTrackTimes = trackTimeService.sumAllTrackTimes(name, className, methodName);
        log.info("Получена сумма статистики по параметрам name = {}, className = {}, methodName = {}, сумма = {}.",
                name, className, methodName, sumAllTrackTimes);
        return ResponseEntity.ok().body(sumAllTrackTimes);
    }

    @GetMapping("/avg")
    @Operation(
            summary = "Получить среднее арифметическое всей статистики",
            description = "Позволяет возвратить информацию о среднем арифметическом всей статистики по имени аннотации, классу и методу, вызвавшим аннотацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = TrackTimeDto.class)))})})
    /**
     * Возвращение информации о среднем арифметическом всей статистики по имени аннотации, классу и методу, вызвавшим аннотацию.
     */
    public ResponseEntity<Double> avgAllTrackTimes(
            @RequestParam(name = "name", required = false)
            @Parameter(description = "наименование аннотации") String name,
            @RequestParam(name = "className", required = false)
            @Parameter(description = "имя класса") String className,
            @RequestParam(name = "methodName", required = false)
            @Parameter(description = "имя метода") String methodName) {
        Double avgAllTrackTimes = trackTimeService.avgAllTrackTimes(name, className, methodName);
        log.info("Получено среднее арифметическое статистики по параметрам name = {}, className = {}, methodName = {}, " +
                "среднее арифметическое = {}.", name, className, methodName, avgAllTrackTimes);
        return ResponseEntity.ok().body(avgAllTrackTimes);
    }

}