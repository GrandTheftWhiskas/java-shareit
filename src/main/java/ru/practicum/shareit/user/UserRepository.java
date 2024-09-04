package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private long id = 1;
    private final Map<Long, User> users = new HashMap();
    private List<String> email = new ArrayList<>();

    public User post(User user) {
        System.out.println("Добавление пользователя");
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

    public User update(Long userId, User user) {
        System.out.println("Обновление пользователя");
        User user1 = get(userId);
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        return post(user1);
    }

    public User get(Long userId) {
        System.out.println("Получение пользователя");
        return users.get(userId);
    }

    public User delete(Long userId) {
        return users.remove(userId);
    }

    public List<String> getEmail() {
        return email;
    }
}

