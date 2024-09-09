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
    private final Map<Long, ItemDto> items = new HashMap<>();
    public ItemDto post(Long userId, ItemDto item) {
        if (item.getId() == null) {
            item.setId(id);
            item.setOwner(userId);
            id++;
        }

        items.put(item.getId(), item);
        return item;
    }

    public ItemDto update(Long itemId, ItemDto item) {
        ItemDto savedItem = items.get(itemId);
        savedItem.setName(item.getName());
        savedItem.setDescription(item.getDescription());
        savedItem.setAvailable(item.getAvailable());
        items.put(savedItem.getId(), savedItem);
        return savedItem;
    }

    public ItemDto get(Long itemId) {
        return items.get(itemId);
    }

    public List<ItemDto> getAll(Long userId) {
        List<ItemDto> itemList = new ArrayList<>();
        for (ItemDto item : items.values()) {
            if (item.getOwner().equals(userId)) {
                itemList.add(item);
            }
        }
        return itemList;
    }

    public List<ItemDto> search(Long userId, String text) {
        String lowerCaseText = text.toLowerCase();
        return getAll(userId).stream().filter(item -> (item.getName().toLowerCase()
                .contains(lowerCaseText) || item.getDescription().toLowerCase().contains(lowerCaseText))
                && item.isAvailable()).toList();
    }
}
