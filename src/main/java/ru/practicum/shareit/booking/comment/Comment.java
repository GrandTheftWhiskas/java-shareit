package ru.practicum.shareit.booking.comment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

@Data
@Table(name = "comments")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "text")
    private String text;
    @ManyToOne
    @JoinColumn(name = "item")
    private Item item;
    @Column(name = "author_name")
    private String authorName;
    @Column(name = "created")
    private LocalDateTime created;
}
