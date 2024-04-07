package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

@Schema(name = "Page<ItemDto>", description = "Page of ItemDto")
public abstract class ItemPage implements Page<ItemDto> {

}