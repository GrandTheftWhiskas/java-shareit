package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithDate;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.Create;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")

public class ItemController {
    private final ItemService itemService;
    private static final String HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ItemDto post(@RequestHeader(HEADER) Long userId, @RequestBody ItemDto item) {
        return itemService.post(userId, item);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto postComment(@RequestHeader(HEADER) Long userId, @RequestBody CommentDto commentDto,
                               @PathVariable Long itemId) {
        return itemService.postComment(userId, commentDto, itemId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(HEADER) Long userId, @PathVariable Long itemId,
                       @RequestBody ItemDto item) {
        return itemService.update(userId, itemId, item);
    }

    @GetMapping("/{itemId}")
    public ItemDtoWithDate get(@PathVariable Long itemId) {
        return itemService.get(itemId);
    }

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader (HEADER) Long userId) {
        return itemService.getAll(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestHeader(HEADER) Long userId, @RequestParam String text) {
        return itemService.search(userId, text);
    }



}

