package cat.itacademy.s04.t01.userapi.repositories;

import cat.itacademy.s04.t01.userapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private UserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void saveShouldIncludeUserInList() {

        int initialListSize = repository.findAll().size();

        User testUser = new User("Test User 1", "example1@email.com");
        repository.save(testUser);

        assert(repository.findAll().size() == initialListSize + 1);
        assert(repository.findAll().getFirst().equals(testUser));

    }

    @Test
    void findAllShouldReturnEmptyAtStart() {
        assert(repository.findAll().isEmpty());
    }

    @Test
    void findByIdShouldReturnExpectedUser() {
        User testUser = new User("Test User 2", "example2@email.com");
        testUser.setId(UUID.randomUUID());
        UUID assignedId = testUser.getId();

        repository.save(testUser);

        assertEquals(testUser, repository.findById(assignedId).orElseThrow());

    }

    @Test
    void searchByNameShouldReturnExpectedUsers() {
        User testUser1 = new User("Name 1", "user1@email.com");
        User testUser2 = new User("Name 2", "user2@email.com");

        repository.save(testUser1);
        repository.save(testUser2);

        assertEquals(1, repository.searchByName("Name 1").size());
        assertEquals("Name 1", repository.searchByName("Name 1").getFirst().getName());
    }

    @Test
    void existsByEmailShouldReturnTrueWhenSameEmailIsPresent() {

        User testUser1 = new User("Name 1", "presentEmail@email.com");

        repository.save(testUser1);

        assertTrue(repository.existsByEmail("presentEmail@email.com"));
        assertFalse(repository.existsByEmail("nonPresentEmail@email.com"));

    }

    @Test
    void deleteByIdShouldRemoveUserWithSameId() {

        User testUser1 = new User("Name 1", "user1@email.com");
        repository.save(testUser1);
        
    }
}