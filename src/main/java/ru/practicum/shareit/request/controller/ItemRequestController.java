package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService requestService;
    private static final String HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ItemRequestDto postRequest(@RequestHeader(HEADER) Long userId, @RequestBody ItemRequestDto itemRequestDto) {
        return requestService.postRequest(userId, itemRequestDto);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequest(@RequestHeader(HEADER) Long userId, @PathVariable Long requestId) {
        return requestService.getRequest(userId, requestId);
    }

    @GetMapping
    public List<ItemRequestDto> getRequests(@RequestHeader(HEADER) Long userId) {
        return requestService.getRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests(@RequestHeader(HEADER) Long userId) {
        return requestService.getAllRequests(userId);
    }
}
