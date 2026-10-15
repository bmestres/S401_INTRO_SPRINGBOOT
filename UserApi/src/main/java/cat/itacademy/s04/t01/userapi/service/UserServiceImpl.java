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

        userRepository.save(user);

        return user;
    }

    private void checkCreateInputData(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User must not be NULL");
        }

        if(this.userRepository.existsByEmail(user.getEmail())) {
            throw new ExistingUserException(String.format("User with email %s already exists", user.getEmail()));
        }

        if(this.userRepository.findById(user.getId()).isPresent()) {
            throw new ExistingUserException(String.format("User with ID %s already exists", user.getId().toString()));
        }
    }

    @Override
    public User updateUser(User user) {
        checkUpdateInputData(user);
        return null;
    }

    private void checkUpdateInputData(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User must not be NULL");
        }
        if(!this.userRepository.existsByEmail(user.getEmail())) {
            throw new ExistingUserException(String.format("User with email %s not found", user.getEmail()));
        }
    }

    @Override
    public User deleteUser(User user) {
        return null;
    }

    @Override
    public User findById(UUID id) {
        Optional<User> foundUser = this.userRepository
                .findAll()
                .stream()
                .filter(user-> id.equals(user.getId()))
                .findFirst();

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

        return this.userRepository
                .findAll()
                .stream()
                .filter(user -> name.equalsIgnoreCase(user.getName()))
                .collect(Collectors.toList());
    }
}
