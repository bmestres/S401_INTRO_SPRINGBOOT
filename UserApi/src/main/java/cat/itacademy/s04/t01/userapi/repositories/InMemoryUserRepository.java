package cat.itacademy.s04.t01.userapi.repositories;

import cat.itacademy.s04.t01.userapi.exceptions.ExistingUserException;
import cat.itacademy.s04.t01.userapi.exceptions.ResourceNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> userList;

    public InMemoryUserRepository(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public User save(User user) {

        if(user == null) {
            throw new IllegalArgumentException("User must not be NULL");
        }

        if(existsByEmail(user.getEmail())) {
            throw new ExistingUserException(String.format(
                    "User with email %s already exists", user.getEmail()));
        }

        this.userList.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(this.userList);
    }

    @Override
    public Optional<User> findById(UUID id) {
        if(id == null) {
            throw new IllegalArgumentException("Id must not be NULL");
        }
        return this.userList
                .stream()
                .filter(user -> id.equals(user.getId()))
                .findFirst();
    }

    @Override
    public List<User> searchByName(String name) {

        if(name == null) {
            throw new IllegalArgumentException("Name must not be NULL");
        }
        return this.userList
                .stream()
                .filter(user-> name.equalsIgnoreCase(user.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {

        if(email == null) {
            throw new IllegalArgumentException("Email must not be NULL");
        }

        Optional<User> foundUser = this.userList
                .stream()
                .filter(user-> email.equalsIgnoreCase(user.getEmail()))
                .findAny();

        return foundUser.isPresent();
    }
}
