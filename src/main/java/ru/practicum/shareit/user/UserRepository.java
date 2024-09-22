package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class UserRepository {
    private long id = 1;
    private final Map<Long, User> users = new HashMap();
    private Set<String> email = new HashSet<>();

    public User post(User user) {
        log.info("Добавление пользователя");
            User saveUser = new User();
            saveUser.setId(id);
            saveUser.setName(user.getName());
            saveUser.setEmail(user.getEmail());
            users.put(id, saveUser);
            email.add(user.getEmail());
            id++;
        return saveUser;
    }

    public User update(Long userId, User user) {
        log.info("Обновление пользователя");
        User savedUser = users.get(userId);
        final String name = user.getName();
        if (name != null && !name.isBlank()) {
            savedUser.setName(name);
        }
        email.remove(savedUser.getEmail());
        final String mail = user.getEmail();
        if (mail != null && !mail.isBlank()) {
            savedUser.setEmail(mail);
        }
        email.add(savedUser.getEmail());
        return savedUser;
    }

    public User get(Long userId) {
        log.info("Получение пользователя");
        return users.get(userId);
    }

    public User delete(Long userId) {
        return users.remove(userId);
    }

    public List<String> getEmail() {
        return new ArrayList<>(email);
    }
}

