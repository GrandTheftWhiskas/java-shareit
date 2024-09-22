package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;
    private static final String HEADER = "X-Sharer-User-Id";

    @PostMapping
    public Booking post(@RequestHeader(HEADER) Long userId, @RequestBody BookingDto bookingDto) {
       return bookingService.post(userId, bookingDto);
    }

    @PatchMapping("{bookingId}")
    public Booking update(@RequestHeader(HEADER) Long userId, @PathVariable Long bookingId,
                          @RequestParam Boolean approved) {
        return bookingService.update(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public Booking getBooking(@RequestHeader(HEADER) Long userId, @PathVariable Long bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping
    public List<Booking> getAllBooking(@RequestHeader(HEADER) Long userId) {
        return bookingService.getAllBookingByUserId(userId);
    }

    @GetMapping("/owner")
    List<Booking> getAllBookingByOwner(@RequestHeader(HEADER) Long userId) {
        return bookingService.getAllBookingByOwnerId(userId);
    }
}
