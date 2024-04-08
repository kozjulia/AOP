package com.example.mapper;

import com.example.dto.ItemDto;
import com.example.model.Item;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto toItemDto(Item item);

    List<ItemDto> convertItemListToItemDtoList(List<Item> list);

}