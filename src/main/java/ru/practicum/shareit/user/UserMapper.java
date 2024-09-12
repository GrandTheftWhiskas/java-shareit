package ru.practicum.shareit.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.exception.NotFoundException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserDto toUserDto(User user) {
        try {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            return userDto;
        } catch (NullPointerException e) {
            throw new NotFoundException("Пользователь не найден");
        }
    }
}