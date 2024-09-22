package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemDbRepository;
import ru.practicum.shareit.user.UserDbRepository;

import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookingService {
    private final ItemDbRepository itemDbRepository;
    private final UserDbRepository userDbRepository;
    private final BookingDbRepository bookingDbRepository;

    public Booking post(Long userId, BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setBooker(userDbRepository.getUserById(userId));
        booking.setItem(itemDbRepository.getItemById(bookingDto.getItemId()));
        booking.setStatus(Status.WAITING);
        if (booking.getItem() == null) {
            throw new NotFoundException("Предмет не найден");
        }
        if (booking.getStart() == null) {
            throw new ValidationException("Старт не может быть пустым");
        }
        if (booking.getEnd() == null) {
            throw new ValidationException("Окончание не может быть пустым");
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

        return bookingDbRepository.save(booking);
    }

    public Booking update(Long userId, Long bookingId, Boolean approved) {
        Booking booking = getBooking(userId, bookingId);
        if (!booking.getItem().getOwner().equals(userId)) {
            throw new ValidationException("Данный пользователь не является владельцем");
        }

        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return bookingDbRepository.save(booking);
    }

    public Booking getBooking(Long userId, Long bookingId) {
        Booking booking = bookingDbRepository.getBookingById(bookingId);
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().equals(userId)) {
            throw new ValidationException("Данный пользователь не является владельцем");
        }
        return booking;
    }

    public List<Booking> getAllBookingByUserId(Long userId) {
        return bookingDbRepository.findAllBookingByBookerId(userId).stream()
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .toList();
    }

    public List<Booking> getAllBookingByOwnerId(Long userId) {
        if (userDbRepository.getUserById(userId) == null) {
            throw new ValidationException("Пользователя не существует");
        }
        return bookingDbRepository.findAllBookingByBookerId(userId).stream()
                .filter(booking -> booking.getItem().getOwner().equals(userId))
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .toList();
    }
}
