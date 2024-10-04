package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.Create;


@Controller
@RequestMapping(path = "/items")
@AllArgsConstructor
@Slf4j
@Validated
public class ItemController {
    @Autowired
    private final ItemClient itemClient;
    private static final String HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> post(@RequestHeader(HEADER) Long userId, @RequestBody @Validated(Create.class) ItemDto item) {
        return itemClient.post(userId, item);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> postComment(@RequestHeader(HEADER) Long userId,
                                              @Valid @RequestBody CommentDto commentDto, @PathVariable Long itemId) {
        return itemClient.post(userId, commentDto, itemId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader(HEADER) Long userId, @PathVariable Long itemId,
                       @RequestBody ItemDto item) {
        return itemClient.patch(userId, itemId, item);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> get(@PathVariable Long itemId) {
        return itemClient.get(itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestHeader (HEADER) Long userId) {
        return itemClient.getAll(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestHeader(HEADER) Long userId, @RequestParam String text) {
        return itemClient.search(userId, text);
    }



}

