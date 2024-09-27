package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.mapper.ItemMapperWithDate;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithDate;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final BookingRepository bookingDbRepository;
    private final CommentRepository commentDbRepository;
    private final ItemRepository itemDbRepository;
    private final UserRepository userDbRepository;

    @Transactional
    public ItemDto post(Long userId, ItemDto itemDto) {
        log.info("Добавление предмета");
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        if (userDbRepository.getUserById(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        item.setOwner(userId);
        return ItemMapper.toItemDto(itemDbRepository.save(item));
    }

    @Transactional
    public CommentDto postComment(Long userId, CommentDto commentDto, Long itemId) {
        List<Booking> bookings = bookingDbRepository.findAllBookingByBookerId(userId).stream()
                .filter(booking -> booking.getItem().getId().equals(itemId))
                .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now())).toList();
        if (!bookings.isEmpty()) {
            Comment comment = new Comment();
            comment.setText(commentDto.getText());
            comment.setItem(itemDbRepository.getItemById(itemId));
            comment.setAuthor(userDbRepository.getUserById(userId));
            comment.setCreated(LocalDateTime.now());
            return CommentMapper.toCommentDto(commentDbRepository.save(comment));
        } else {
            throw new ValidationException("Произошла ошибка");
        }
    }

    @Transactional
    public ItemDto update(Long userId, Long itemId, ItemDto itemDto) {
        Item item = itemDbRepository.getItemById(itemId);
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        log.info("Обновление предмета");
        if (!itemDbRepository.getItemById(itemId).getOwner().equals(userId)) {
            throw new NotFoundException("Данный пользователь не является владельцем этого предмета");
        }
        item.setId(itemId);
        item.setOwner(userId);
        return ItemMapper.toItemDto(itemDbRepository.save(item));
    }

    public ItemDtoWithDate get(Long itemId) {
        log.info("Получение предмета");
        ItemDtoWithDate itemDto = ItemMapperWithDate.toItemDto(itemDbRepository.getItemById(itemId));
        itemDto.setComments(commentDbRepository.findAllCommentByItemId(itemId).stream()
                .map(comment -> CommentMapper.toCommentDto(comment)).toList());
        return itemDto;
    }

    public List<ItemDto> getAll(Long userId) {
        log.info("Получение предметов");
        return itemDbRepository.findAllItemByOwner(userId).stream().map(item -> ItemMapper.toItemDto(item)).toList();
    }

    public List<ItemDto> search(Long userId, String text) {
        log.info("Поиск предмета");
        if (userDbRepository.getUserById(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        } else if (text.isBlank()) {
            return Collections.emptyList();
        }

        return itemDbRepository.search(text).stream()
                .map(item -> ItemMapper.toItemDto(item)).toList();
    }
}
