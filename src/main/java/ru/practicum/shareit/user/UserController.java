package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Validated(Create.class)
    public UserDto post(@RequestBody User user) {
        return userService.post(user);
    }

    @PatchMapping("/{userId}")
    @Validated(Update.class)
    public UserDto update(@PathVariable Long userId, @RequestBody User user) {
        return userService.update(userId, user);
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable Long userId) {
        return userService.get(userId);
    }

    @DeleteMapping("/{userId}")
    public UserDto delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }
}
