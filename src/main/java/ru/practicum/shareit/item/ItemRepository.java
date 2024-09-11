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

    public Item post(Long userId, ItemDto item) {
        Item saveItem = new Item();
        saveItem.setId(id);
        id++;
        saveItem.setName(item.getName());
        saveItem.setDescription(item.getDescription());
        saveItem.setAvailable(item.getAvailable());
        saveItem.setOwner(userId);
        items.put(saveItem.getId(), saveItem);
        return saveItem;
    }

    public Item update(Long itemId, ItemDto item) {
        Item savedItem = items.get(itemId);
        final String name = item.getName();
        if (name != null && !name.isBlank()) {
            savedItem.setName(name);
        }

        final String description = item.getDescription();
        if (description != null && !description.isBlank()) {
            savedItem.setDescription(description);
        }

        final Boolean available = item.getAvailable();
        if (available != null) {
            savedItem.setAvailable(available);
        }
        return savedItem;
    }

    public Item get(Long itemId) {
        return items.get(itemId);
    }

    public List<Item> getAll(Long userId) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getOwner().equals(userId)) {
                itemList.add(item);
            }
        }
        return itemList;
    }

    public List<Item> search(Long userId, String text) {
        String lowerCaseText = text.toLowerCase();
        return getAll(userId).stream().filter(item -> (item.getName().toLowerCase()
                .contains(lowerCaseText) || item.getDescription().toLowerCase().contains(lowerCaseText))
                && item.getAvailable()).toList();
    }
}
