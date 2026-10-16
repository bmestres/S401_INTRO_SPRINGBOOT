package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.exceptions.ExistingUserException;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {

        checkCreateInputData(user);

        user.setId(UUID.randomUUID());
        userRepository.save(user);

        return user;
    }

    @Override
    public List<User> getAllUsers(String userName) {

        if(userName == null || userName.trim().isBlank()) {
            return userRepository.findAll();
        }
        return userRepository.searchByName(userName);
    }


    @Override
    public void deleteUser(User user) {

        if(user == null) {
            throw new IllegalArgumentException("User must not be NULL");
        }

        Optional<User> foundUser = this.userRepository.findById(user.getId());

        if(foundUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID %s not found", user.getId().toString()));
        }

        this.userRepository.deleteById(user.getId());
    }

    @Override
    public User findById(UUID id) {

        if(id == null) {
            throw new IllegalArgumentException("ID must not be NULL");
        }

        Optional<User> foundUser = this.userRepository.findById(id);

        if(foundUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID %s not found", id.toString()));
        }
        return foundUser.get();
    }

    @Override
    public List<User> filterByName(String name) {

        if(name == null) {
            throw new IllegalArgumentException("Name must not be NULL");
        }

        return this.userRepository.searchByName(name);
    }

    private void checkCreateInputData(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User must not be NULL");
        }

        if(this.userRepository.existsByEmail(user.getEmail())) {
            throw new ExistingUserException(String.format("User with email %s already exists", user.getEmail()));
        }
    }
}
