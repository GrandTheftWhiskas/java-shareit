package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@NotBlank(groups = {Create.class})
public class UserDto {
    private Long id;
    private String name;
    @Email
    private String email;
}

