package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoAnswer;
import ru.practicum.shareit.item.model.Item;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setOwner(item.getOwner());
        itemDto.setRequestId(item.getRequestId());
        return itemDto;
    }

    public static ItemDtoAnswer toDtoAnswer(Item item) {
        ItemDtoAnswer itemDtoAnswer = new ItemDtoAnswer();
        itemDtoAnswer.setItemId(item.getId());
        itemDtoAnswer.setName(item.getName());
        itemDtoAnswer.setOwnerId(item.getOwner());
        return itemDtoAnswer;
    }
}
