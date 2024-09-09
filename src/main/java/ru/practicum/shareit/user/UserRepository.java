package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class UserRepository {
    private long id = 1;
    private final Map<Long, UserDto> users = new HashMap();
    private List<String> email = new ArrayList<>();

    public UserDto post(UserDto user) {
        log.info("Добавление пользователя");
        if (user.getId() == null) {
            user.setId(id);
            users.put(id, user);
            email.add(user.getEmail());
            id++;
        } else {
            users.put(id, user);
            email.add(user.getEmail());
        }
        return user;
    }

    public UserDto update(Long userId, UserDto user) {
        log.info("Обновление пользователя");
        UserDto savedUser = users.get(userId);
        savedUser.setName(user.getName());
        savedUser.setEmail(user.getEmail());
        users.put(id, savedUser);
        email.add(user.getEmail());
        return savedUser;
    }

    public UserDto get(Long userId) {
        log.info("Получение пользователя");
        return users.get(userId);
    }

    public UserDto delete(Long userId) {
        return users.remove(userId);
    }

    public List<String> getEmail() {
        return email;
    }
}

