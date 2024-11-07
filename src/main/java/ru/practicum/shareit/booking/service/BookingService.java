package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BookingService {
    private final ItemRepository itemDbRepository;
    private final UserRepository userDbRepository;
    private final BookingRepository bookingDbRepository;

    @Transactional
    public BookingDto post(Long userId, BookingDto bookingDto) {
        log.info("Добавление бронирования");
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setBooker(userDbRepository.getUserById(userId));
        booking.setItem(itemDbRepository.getItemById(bookingDto.getItemId()));
        booking.setStatus(Status.WAITING);
        if (booking.getItem() == null) {
            throw new NotFoundException("Предмет не найден");
        }
        if (!itemDbRepository.getItemById(booking.getItem().getId()).getAvailable()) {
            throw new ValidationException("Предмет недоступен");
        }
        if (booking.getStart().equals(booking.getEnd())) {
            throw new ValidationException("Даты не могут быть равны");
        }
        if (booking.getEnd().isBefore(booking.getStart())) {
            throw new ValidationException("Окончание не может быть раньше старта");
        }
        return BookingMapper.toBookingDto(bookingDbRepository.save(booking));
    }

    @Transactional
    public BookingDto update(Long userId, Long bookingId, Boolean approved) {
        log.info("Обновление бронирования");
        Booking booking = bookingDbRepository.getBookingById(bookingId);
        if (!booking.getItem().getOwner().equals(userId)) {
            throw new ValidationException("Данный пользователь не является владельцем");
        }

        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return BookingMapper.toBookingDto(bookingDbRepository.save(booking));
    }

    public BookingDto getBooking(Long userId, Long bookingId) {
        log.info("Получение бронирования");
        Booking booking = bookingDbRepository.getBookingById(bookingId);
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().equals(userId)) {
            throw new ValidationException("Данный пользователь не является владельцем");
        }
        return BookingMapper.toBookingDto(booking);
    }

    public List<BookingDto> getAllBookingByUserId(Long userId) {
        return  bookingDbRepository.findAllBookingByBookerId(userId).stream()
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .map(booking -> BookingMapper.toBookingDto(booking)).toList();
    }

    public List<BookingDto> getAllBookingByOwnerId(Long userId) {
        if (userDbRepository.getUserById(userId) == null) {
            throw new ValidationException("Пользователя не существует");
        }
        return bookingDbRepository.findAllBookingByBookerId(userId).stream()
                .filter(booking -> booking.getItem().getOwner().equals(userId))
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .map(booking -> BookingMapper.toBookingDto(booking)).toList();
    }
}
