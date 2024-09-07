package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;


@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository repository;

    public UserDto post(User user) {
        if (user.getEmail() == null) {
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        if (repository.getEmail().contains(user.getEmail())) {
            throw new IllegalArgumentException("Указанный email уже существует");
        }

        if (!user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email должен содержать символ @");
        }

        return repository.post(user);
    }


    public UserDto update(Long userId, User user) throws ValidationException {
        if (repository.getEmail().contains(user.getEmail())) {
            throw new IllegalArgumentException("Указанный email уже существует");
        }

        return repository.update(userId, user);
    }


    public UserDto get(Long userId) {
        return repository.get(userId);
    }


    public UserDto delete(Long userId) {
        return repository.delete(userId);
    }
}
