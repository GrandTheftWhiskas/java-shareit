package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private long id = 1;
    private final Map<Long, Item> items = new HashMap<>();

    public Item post(Long userId, Item item) {
        if (item.getId() == null) {
            item.setId(id);
            item.setOwner(userId);
            id++;
        }

        items.put(item.getId(), item);
        return item;
    }

    public Item update(Long itemId, Item item) {
        Item item1 = items.get(itemId);
        item1.setName(item.getName());
        item1.setDescription(item.getDescription());
        item1.setAvailable(item.getAvailable());
        return post(item1.getId(), item1);
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

    public List<Item> search(String text) {
        return items.values().stream().filter(item -> item.isAvailable() && item.getName().toUpperCase()
                .contains(text) || item.getDescription().toUpperCase().contains(text)).toList();
    }
}
