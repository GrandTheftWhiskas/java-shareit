package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemDto post(Long userId, Item item) {
        if (userRepository.get(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        } else if (item.getAvailable() == null) {
            throw new IllegalArgumentException("Статус не может быть пустым");
        } else if (item.getName() == null || item.getName().isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        } else if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }
        return itemRepository.post(userId, item);
    }

    public ItemDto update(Long userId, Long itemId, Item item) {
        if (!itemRepository.get(itemId).getOwner().equals(userId)) {
            throw new NotFoundException("Данный пользователь не является владельцем этого предмета");
        }
        return itemRepository.update(itemId, item);
    }

    public ItemDto get(Long itemId) {
        return itemRepository.get(itemId);
    }

    public List<ItemDto> getAll(Long userId) {
        return itemRepository.getAll(userId);
    }

    public List<ItemDto> search(Long userId, String text) {
        if (userRepository.get(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        } else if (text.isBlank()) {
            return new ArrayList<>();
        }

        return itemRepository.search(userId, text);
    }
}
