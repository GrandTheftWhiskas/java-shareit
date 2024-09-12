package ru.practicum.shareit.user;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository repository;

    public UserDto post(UserDto user) {
        if (repository.getEmail().contains(user.getEmail())) {
            throw new IllegalArgumentException("Указанный email уже существует");
        }

        return UserMapper.toUserDto(repository.post(user));
    }


    public UserDto update(Long userId, UserDto user) {
        if (!Objects.equals(repository.get(userId).getEmail(), user.getEmail())) {
            if (repository.getEmail().contains(user.getEmail())) {
                throw new IllegalArgumentException("Указанный email уже существует");
            }
        }
        return UserMapper.toUserDto(repository.update(userId, user));
    }


    public UserDto get(Long userId) {
        return UserMapper.toUserDto(repository.get(userId));
    }


    public UserDto delete(Long userId) {
        return UserMapper.toUserDto(repository.delete(userId));
    }
}
