package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.ItemRequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    @Autowired
    private final ItemRequestClient itemRequestClient;
    private static final String HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> postRequest(@RequestHeader(HEADER) Long userId, @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestClient.post(userId, itemRequestDto);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@RequestHeader(HEADER) Long userId, @PathVariable Long requestId) {
        return itemRequestClient.get(userId, requestId);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader(HEADER) Long userId) {
        return itemRequestClient.get(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(HEADER) Long userId) {
        return itemRequestClient.getAll(userId);
    }
}
