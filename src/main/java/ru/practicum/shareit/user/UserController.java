package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User post(@RequestBody User user) {
        return userService.post(user);
    }

    @PatchMapping("/{userId}")
    public User update(@PathVariable Long userId, @RequestBody User user) {
        return userService.update(userId, user);
    }

    @GetMapping("/{userId}")
    public User get(@PathVariable Long userId) {
        return userService.get(userId);
    }

    @DeleteMapping("/{userId}")
    public User delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }
}
