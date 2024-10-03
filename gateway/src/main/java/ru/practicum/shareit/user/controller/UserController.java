package ru.practicum.shareit.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.Create;
import ru.practicum.shareit.user.Update;
import ru.practicum.shareit.user.dto.UserDto;


@Controller
@RequestMapping(path = "/users")
@AllArgsConstructor
@Slf4j
@Validated
public class UserController {
    @Autowired
    private final UserClient userClient;

    @PostMapping
    @Validated(Create.class)
    public ResponseEntity<Object> post(@RequestBody @Validated(Create.class) UserDto user) {
        return userClient.post(user);
    }

    @PatchMapping("/{userId}")
    @Validated(Update.class)
    public ResponseEntity<Object> update(@PathVariable Long userId, @RequestBody @Validated(Update.class) UserDto user) {
        return userClient.update(userId, user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> get(@PathVariable Long userId) {
        return userClient.get(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable Long userId) {
        return userClient.delete(userId);
    }
}
