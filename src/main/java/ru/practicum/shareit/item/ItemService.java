package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingDbRepository;
import ru.practicum.shareit.booking.comment.Comment;
import ru.practicum.shareit.booking.comment.CommentDbRepository;
import ru.practicum.shareit.booking.comment.CommentDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithDate;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserDbRepository;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final BookingDbRepository bookingDbRepository;
    private final CommentDbRepository commentDbRepository;
    private final ItemDbRepository itemDbRepository;
    private final UserDbRepository userDbRepository;

    public ItemDto post(Long userId, Item item) {
        System.out.println("Добавление предмета");
        if (userDbRepository.getUserById(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        item.setOwner(userId);
        return ItemMapper.toItemDto(itemDbRepository.save(item));
    }

    public Comment postComment(Long userId, CommentDto commentDto, Long itemId) {
        List<Booking> bookings = bookingDbRepository.findAllBookingByBookerId(userId).stream()
                .filter(booking -> booking.getItem().getId().equals(itemId))
                .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now())).toList();
        if (!bookings.isEmpty()) {
            Comment comment = new Comment();
            comment.setText(commentDto.getText());
            comment.setItem(itemDbRepository.getItemById(itemId));
            comment.setAuthorName(userDbRepository.getUserById(userId).getName());
            comment.setCreated(LocalDateTime.now());
            return commentDbRepository.save(comment);
        } else {
            throw new ValidationException("Произошла ошибка");
        }
    }

    public ItemDto update(Long userId, Long itemId, Item item) {
        System.out.println("Обновление предмета");
        if (!itemDbRepository.getItemById(itemId).getOwner().equals(userId)) {
            throw new NotFoundException("Данный пользователь не является владельцем этого предмета");
        }
        item.setId(itemId);
        item.setOwner(userId);
        return ItemMapper.toItemDto(itemDbRepository.save(item));
    }

    public ItemDtoWithDate get(Long itemId) {
        System.out.println("Получение предмета");
        ItemDtoWithDate itemDto = ItemMapperWithDate.toItemDto(itemDbRepository.getItemById(itemId));
        itemDto.setComments(commentDbRepository.findAllCommentByItemId(itemId));
        return itemDto;
    }

    public List<ItemDto> getAll(Long userId) {
        System.out.println("Получение предметов");
        return itemDbRepository.findAllItemByOwner(userId).stream().map(item -> ItemMapper.toItemDto(item)).toList();
    }

    public List<ItemDto> search(Long userId, String text) {
        System.out.println("Поиск предмета");
        if (userDbRepository.getUserById(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        } else if (text.isBlank()) {
            return new ArrayList<>();
        }

        return itemDbRepository.search(text).stream()
                .map(item -> ItemMapper.toItemDto(item)).toList();
    }
}
