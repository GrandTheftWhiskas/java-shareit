package ru.practicum.shareit.user;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository repository;
    private final UserDbRepository userDbRepository;

    public UserDto post(User user) {
        System.out.println("Добавление пользователя");
        if (repository.getEmail().contains(user.getEmail())) {
            throw new IllegalArgumentException("Указанный email уже существует");
        }

        return UserMapper.toUserDto(userDbRepository.save(user));
    }


    public UserDto update(Long userId, User user) {
        System.out.println("Обновление пользователя");
        if (!Objects.equals(userDbRepository.getUserById(userId).getEmail(), user.getEmail())) {
            if (userDbRepository.getEmail().contains(user.getEmail())) {
                throw new IllegalArgumentException("Указанный email уже существует");
            }
        }
        user.setId(userId);
        return UserMapper.toUserDto(userDbRepository.save(user));
    }


    public UserDto get(Long userId) {
        System.out.println("Получение пользователя");
        return UserMapper.toUserDto(userDbRepository.getUserById(userId));
    }


    public void delete(Long userId) {
        System.out.println("Получение пользователей");
        userDbRepository.deleteById(userId);
    }
}
