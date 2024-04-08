package com.example.controller;

import com.example.annotation.TrackAsyncTime;
import com.example.annotation.TrackTime;
import com.example.dto.ItemDto;
import com.example.dto.ItemPage;
import com.example.dto.NewItemDto;
import com.example.dto.UpdateItemDto;
import com.example.service.ItemService;
import com.example.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import static com.example.swagger.ResponseExample.ITEM_ERROR_400_EXAMPLE;
import static com.example.swagger.ResponseExample.ITEM_ERROR_404_EXAMPLE;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Элементы", description = "Взаимодействие с элементами")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemId}")
    @Operation(
            summary = "Получить элемент",
            description = "Позволяет возвратить информацию об элементе по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))}),
            @ApiResponse(responseCode = "404", description = "The required object was not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(value = ITEM_ERROR_404_EXAMPLE)})})})
    @TrackTime
    /**
     * Возвращение информации об элементе по id.
     */
    public ResponseEntity<ItemDto> getItemById(
            @PathVariable @Parameter(description = "Идентификатор элемента", required = true) Long itemId) {
        ItemDto itemDto = itemService.getItemById(itemId);
        return ResponseEntity.ok().body(itemDto);
    }

    @GetMapping("/async/{itemId}")
    @Operation(
            summary = "Получить асинхронно элемент",
            description = "Позволяет асинхронно возвратить информацию об элементе по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))}),
            @ApiResponse(responseCode = "404", description = "The required object was not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(value = ITEM_ERROR_404_EXAMPLE)})})})
    @TrackAsyncTime
    /**
     * Возвращение асинхронно информации об элементе по id.
     */
    public CompletableFuture<ResponseEntity<ItemDto>> getAsyncItemById(
            @PathVariable @Parameter(description = "Идентификатор элемента", required = true) Long itemId) {
        return CompletableFuture.supplyAsync(() -> itemService.getItemById(itemId))
                .thenApply(itemDto -> ResponseEntity.ok().body(itemDto));
    }

    @GetMapping
    @Operation(
            summary = "Получить все элементы",
            description = "Позволяет возвратить информацию о всех элементах по ресторану, категории, сортировке")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemPage.class))})})
    @TrackTime
    /**
     * Возвращение информации об элементах.
     */
    public ResponseEntity<Page<ItemDto>> getAllItems(
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
            @Parameter(description = "номер страницы") Integer from,
            @Positive @Max(500) @RequestParam(name = "size", defaultValue = "10")
            @Parameter(description = "размер страницы") Integer size) {
        Page<ItemDto> itemDtos = itemService.getAllItems(from, size);
        log.info("Получен список элементов, from = {}, size = {}, количество = {}.", from, size, itemDtos.stream().count());
        return ResponseEntity.ok().body(itemDtos);
    }

    @GetMapping("/async")
    @Operation(
            summary = "Получить асинхронно все элементы",
            description = "Позволяет асинхронно возвратить информацию о всех элементах по ресторану, категории, сортировке")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemPage.class))})})
    @TrackAsyncTime
    /**
     * Возвращение асинхронно информации об элементах.
     */
    public CompletableFuture<ResponseEntity<Page<ItemDto>>> getAsyncAllItems(
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
            @Parameter(description = "номер страницы") Integer from,
            @Positive @Max(500) @RequestParam(name = "size", defaultValue = "10")
            @Parameter(description = "размер страницы") Integer size) {
        return CompletableFuture.supplyAsync(() -> itemService.getAllItems(from, size))
                .thenApply(itemDtos ->
                {
                    log.info("Получен список элементов, from = {}, size = {}, количество = {}.", from, size, itemDtos.stream().count());
                    return ResponseEntity.ok().body(itemDtos);
                });
    }

    @PostMapping
    @Validated
    @Operation(
            summary = "Добавить элемент",
            description = "Позволяет добавить новый элемент")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrectly made request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(value = ITEM_ERROR_400_EXAMPLE)})})})
    @TrackTime
    /**
     * Добавление нового элемента.
     */
    public ResponseEntity<ItemDto> saveItem(@Valid @RequestBody NewItemDto newItemDto) {
        ItemDto itemDto = itemService.saveItem(newItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDto);
    }

    @PostMapping("/async")
    @Validated
    @Operation(
            summary = "Добавить асинхронно элемент",
            description = "Позволяет добавить асинхронно новый элемент")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrectly made request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(value = ITEM_ERROR_400_EXAMPLE)})})})
    @TrackAsyncTime
    /**
     * Добавление нового элемента асинхронно.
     */
    public CompletableFuture<ResponseEntity<ItemDto>> saveAsyncItem(@Valid @RequestBody NewItemDto newItemDto) {
        return CompletableFuture.supplyAsync(() -> itemService.saveItem(newItemDto))
                .thenApply(itemDto -> ResponseEntity.status(HttpStatus.CREATED).body(itemDto));
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    @Operation(
            summary = "Редактировать элемент",
            description = "Позволяет редактировать данные элемента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrectly made request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(value = ITEM_ERROR_400_EXAMPLE)})}),
            @ApiResponse(responseCode = "404", description = "The required object was not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = {@ExampleObject(value = ITEM_ERROR_404_EXAMPLE)})})})
    @TrackTime
    @TrackAsyncTime
    /**
     * Редактирование данных элемента.
     */
    public void updateItem(
            @PathVariable @Parameter(description = "Идентификатор элемента", required = true) Long itemId,
            @Valid @RequestBody UpdateItemDto updateItemDto) {
        itemService.updateItem(itemId, updateItemDto);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Удалить элемент",
            description = "Позволяет удалить элемент по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content")})
    @TrackTime
    @TrackAsyncTime
    /**
     * Удаление элемента по id.
     */
    public void deleteItemById(
            @PathVariable @Parameter(description = "Идентификатор элемента", required = true) Long itemId) {
        itemService.deleteItemById(itemId);
    }

}