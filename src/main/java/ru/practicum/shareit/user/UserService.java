package ru.practicum.shareit.user;


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
        return repository.post(user);
    }


    public UserDto update(Long userId, UserDto user) {
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
