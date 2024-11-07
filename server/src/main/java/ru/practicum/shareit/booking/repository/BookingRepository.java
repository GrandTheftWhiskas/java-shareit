package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking save(Booking booking);

    Booking getBookingById(Long bookingId);

    List<Booking> findAllBookingByItemId(Long itemId);

    List<Booking> findAllBookingByBookerId(Long userId);
}
