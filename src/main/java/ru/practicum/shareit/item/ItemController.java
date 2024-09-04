package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item post(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody Item item) {
        return itemService.post(userId, item);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId,
                       @RequestBody Item item) {
        return itemService.update(userId, itemId, item);
    }

    @GetMapping("/{itemId}")
    public Item get(@PathVariable Long itemId) {
        return itemService.get(itemId);
    }

    @GetMapping
    public List<Item> getAll(@RequestHeader ("X-Sharer-User-Id") Long userId) {
        return itemService.getAll(userId);
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam String text) {
        return itemService.search(text);
    }

}

