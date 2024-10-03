package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.Create;
import ru.practicum.shareit.user.Update;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Validated(Create.class)
    public UserDto post(@RequestBody @Validated(Create.class) UserDto user) {
        return userService.post(user);
    }

    @PatchMapping("/{userId}")
    @Validated(Update.class)
    public UserDto update(@PathVariable Long userId, @RequestBody @Validated(Update.class) UserDto user) {
        return userService.update(userId, user);
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable Long userId) {
        return userService.get(userId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
