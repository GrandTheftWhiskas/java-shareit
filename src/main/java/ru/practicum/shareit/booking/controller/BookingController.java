package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;
    private static final String HEADER = "X-Sharer-User-Id";

    @PostMapping
    public BookingDto post(@RequestHeader(HEADER) Long userId, @Valid @RequestBody BookingDto bookingDto) {
       return bookingService.post(userId, bookingDto);
    }

    @PatchMapping("{bookingId}")
    public BookingDto update(@RequestHeader(HEADER) Long userId, @PathVariable Long bookingId,
                          @RequestParam Boolean approved) {
        return bookingService.update(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@RequestHeader(HEADER) Long userId, @PathVariable Long bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getAllBooking(@RequestHeader(HEADER) Long userId) {
        return bookingService.getAllBookingByUserId(userId);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllBookingByOwner(@RequestHeader(HEADER) Long userId) {
        return bookingService.getAllBookingByOwnerId(userId);
    }
}
