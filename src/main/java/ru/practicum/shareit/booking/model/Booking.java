package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_time")
    private LocalDateTime start;
    @Column(name = "end_time")
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "item")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "booker")
    private User booker;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
