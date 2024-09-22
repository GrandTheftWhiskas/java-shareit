package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingDbRepository extends JpaRepository<Booking, Long> {
    Booking save(Booking booking);

    Booking getBookingById(Long bookingId);

    List<Booking> findAllBookingByItemId(Long itemId);

    List<Booking> findAllBookingByBookerId(Long userId);
}
