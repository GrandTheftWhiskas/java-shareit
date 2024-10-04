package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemDtoAnswer {
        private Long itemId;
        @NotBlank
        private String name;
        private Long ownerId;
}
