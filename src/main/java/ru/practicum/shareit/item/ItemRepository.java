package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private long id = 1;
    private final Map<Long, Item> items = new HashMap<>();

    public ItemDto post(Long userId, Item item) {
        if (item.getId() == null) {
            item.setId(id);
            item.setOwner(userId);
            id++;
        }

        items.put(item.getId(), item);
        return ItemMapper.toItemDto(item);
    }

    public ItemDto update(Long itemId, Item item) {
        Item savedItem = items.get(itemId);
        savedItem.setName(item.getName());
        savedItem.setDescription(item.getDescription());
        savedItem.setAvailable(item.getAvailable());
        items.put(savedItem.getId(), savedItem);
        return ItemMapper.toItemDto(savedItem);
    }

    public ItemDto get(Long itemId) {
        return ItemMapper.toItemDto(items.get(itemId));
    }

    public List<ItemDto> getAll(Long userId) {
        List<ItemDto> itemList = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getOwner().equals(userId)) {
                itemList.add(ItemMapper.toItemDto(item));
            }
        }
        return itemList;
    }

    public List<ItemDto> search(Long userId, String text) {
        return getAll(userId).stream().filter(item -> (item.getName().toLowerCase()
                .contains(text.toLowerCase()) || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                && item.isAvailable()).toList();
    }
}
