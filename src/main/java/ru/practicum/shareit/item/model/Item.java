package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.Create;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(groups = Create.class)
    @Column(name = "name", nullable = false)
    private String name;
    @NotBlank(groups = Create.class)
    @Column(name = "description", nullable = false)
    private String description;
    @NotNull(groups = Create.class)
    @Column(name = "available", nullable = false)
    private Boolean available;
    @Column(name = "owner", nullable = false)
    private Long owner;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private ItemRequest itemRequest;
}
