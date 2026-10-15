package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(User user);

    User updateUser(User user);

    User deleteUser(User user);

    User findById(UUID id);

    List<User> filterByName(String name);
}


