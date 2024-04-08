package com.example.mapper;

import com.example.dto.NewItemDto;
import com.example.dto.UpdateItemDto;
import com.example.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemServiceMapper {

    @Mapping(target = "id", ignore = true)
    Item toItemFromNewItemDto(NewItemDto newItemDto);

    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "name", source = "updateItemDto.name")
    @Mapping(target = "description", source = "updateItemDto.description")
    @Mapping(target = "price", source = "updateItemDto.price")
    Item toItemFromUpdateItemDto(Item item, UpdateItemDto updateItemDto);

}