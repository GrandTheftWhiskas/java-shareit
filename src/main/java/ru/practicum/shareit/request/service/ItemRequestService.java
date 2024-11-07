package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public ItemRequestDto postRequest(Long userId, ItemRequestDto itemRequestDto) {
        log.info("Добавление запроса");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemRequestDto.getId());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setRequestor(userId);
        itemRequest.setCreated(LocalDateTime.now());
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    public ItemRequestDto getRequest(Long userId, Long requestId) {
        ItemRequestDto itemRequestDto = ItemRequestMapper
                .toItemRequestDto(itemRequestRepository.getItemRequestById(requestId));
        itemRequestDto.setItems(itemRepository.findAllItemByOwner(userId).stream()
                .map(item -> ItemMapper.toDtoAnswer(item)).toList());
        return itemRequestDto;
    }

    public List<ItemRequestDto> getRequests(Long userId) {
        return itemRequestRepository.getItemRequestsByRequestor(userId).stream()
                .sorted(Comparator.comparing(ItemRequest::getCreated).reversed())
                .map(itemRequest -> ItemRequestMapper.toItemRequestDto(itemRequest)).toList();
    }

    public List<ItemRequestDto> getAllRequests(Long userId) {
        return itemRequestRepository.findAllItemRequest().stream()
                .filter(itemRequest -> !itemRequest.getRequestor().equals(userId))
                .sorted(Comparator.comparing(ItemRequest::getCreated).reversed())
                .map(itemRequest -> ItemRequestMapper.toItemRequestDto(itemRequest)).toList();
    }
}
