package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.exceptions.ResourceNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    private final List<User>userList = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAUsers(@RequestParam(required = false) String name) {

        if(name == null || name.trim().isBlank()) {
            return List.copyOf(this.userList);
        }
        return this.userList
                .stream()
                .filter(user-> user.name().equalsIgnoreCase(name))
                .findFirst()
                .stream()
                .toList();
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {

        User newUser = new User(
                UUID.randomUUID(),
                user.name(),
                user.email()
        );
        this.userList.add(newUser);
        return newUser;
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable UUID id) {
        Optional<User>foundUser = this.userList
                .stream()
                .filter(user-> user.id().equals(id))
                .findFirst();
        if(foundUser.isEmpty()) {
            throw new ResourceNotFoundException("NotFound(404)");
        }
        return foundUser.get();
    }
}
