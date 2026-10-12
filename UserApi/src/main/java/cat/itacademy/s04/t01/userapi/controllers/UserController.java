package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.exceptions.ResourceNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    List<User>userList = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAll() {
        return List.copyOf(this.userList);
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
    public User getUserById(@PathVariable UUID id){
        Optional <User> foundUser = this.userList
                .stream()
                .filter(user-> user.id().equals(id))
                .findFirst();
        if(foundUser.isEmpty()) {
            throw new ResourceNotFoundException("NotFound(404)");
        }
        return foundUser.get();
    }
}
