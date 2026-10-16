package cat.itacademy.s04.t01.userapi.repositories;

import cat.itacademy.s04.t01.userapi.exceptions.ExistingUserException;
import cat.itacademy.s04.t01.userapi.exceptions.ResourceNotFoundException;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> userList = new ArrayList<>();

    @Override
    public User save(User user) {
        this.userList.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(this.userList);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return this.userList
                .stream()
                .filter(user -> id.equals(user.getId()))
                .findFirst();
    }

    @Override
    public List<User> searchByName(String name) {
        return this.userList
                .stream()
                .filter(user-> user.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        Optional<User> foundUser = this.userList
                .stream()
                .filter(user-> email.equalsIgnoreCase(user.getEmail()))
                .findAny();

        return foundUser.isPresent();
    }

    @Override
    public void deleteById(UUID id) {
        Optional<User> user = findById(id);

        if(user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID %s not found", id.toString()));
        }

        this.userList.remove(user.get());
    }
}
