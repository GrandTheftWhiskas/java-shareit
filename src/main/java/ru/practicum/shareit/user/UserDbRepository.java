package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDbRepository extends JpaRepository<User, Long> {

    User save(User user);

    User getUserById(Long userId);

    void deleteById(Long userId);

    @Query("select email from User")
    List<String> getEmail();

}
