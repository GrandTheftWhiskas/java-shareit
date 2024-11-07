package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDtoWithDate;
import ru.practicum.shareit.item.model.Item;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapperWithDate {
        public static ItemDtoWithDate toItemDto(Item item) {
            ItemDtoWithDate itemDto = new ItemDtoWithDate();
            itemDto.setId(item.getId());
            itemDto.setName(item.getName());
            itemDto.setDescription(item.getDescription());
            itemDto.setAvailable(item.getAvailable());
            itemDto.setOwner(item.getOwner());
            return itemDto;
        }
}
