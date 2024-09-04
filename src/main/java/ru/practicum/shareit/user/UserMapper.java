package ru.practicum.shareit.user;

public class UserMapper {
    public static UserDto UserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(userDto.getName());
        userDto.setEmail(userDto.getEmail());
        return userDto;
    }
}