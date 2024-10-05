package ru.practicum.shareit.request.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.ItemRequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    @Autowired
    private final ItemRequestClient itemRequestClient;
    private static final String HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> postRequest(@RequestHeader(HEADER) Long userId,
                                              @Valid @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Добавление запроса");
        return itemRequestClient.post(userId, itemRequestDto);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@RequestHeader(HEADER) Long userId, @PathVariable Long requestId) {
        log.info("Получение запроса");
        return itemRequestClient.get(userId, requestId);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader(HEADER) Long userId) {
        log.info("Получение запросов");
        return itemRequestClient.get(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(HEADER) Long userId) {
        log.info("Получение запросов");
        return itemRequestClient.getAll(userId);
    }
}
