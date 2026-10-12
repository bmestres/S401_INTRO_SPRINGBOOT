package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    List<User>userList = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAll() {
        return List.copyOf(this.userList);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        this.userList.add(user);
        return user;
    }
}
