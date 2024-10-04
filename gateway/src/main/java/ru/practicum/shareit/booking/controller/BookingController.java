package ru.practicum.shareit.booking.controller;

import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.client.BookingClient;
import ru.practicum.shareit.booking.dto.BookingDto;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	@Autowired
	private final BookingClient bookingClient;

	private static final String HEADER = "X-Sharer-User-Id";

	@PostMapping
	public ResponseEntity<Object> post(@RequestHeader(HEADER) Long userId, @Valid @RequestBody BookingDto bookingDto) {
		return bookingClient.post(userId, bookingDto);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> update(@RequestHeader(HEADER) Long userId, @PathVariable Long bookingId,
										 @RequestParam boolean approved) {
		return bookingClient.patch(userId, bookingId, approved);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBooking(@RequestHeader(HEADER) Long userId, @PathVariable Long bookingId) {
		return bookingClient.get(userId, bookingId);
	}

	@GetMapping
	public ResponseEntity<Object> getAllBooking(@RequestHeader(HEADER) Long userId) {
		return bookingClient.getAll(userId);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getAllBookingByOwner(@RequestHeader(HEADER) Long userId) {
		return bookingClient.get(userId);
	}
}

