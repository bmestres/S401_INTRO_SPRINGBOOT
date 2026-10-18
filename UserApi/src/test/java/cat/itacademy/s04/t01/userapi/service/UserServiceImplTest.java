package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.exceptions.ExistingUserException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserShouldThrowExceptionIfEmailExists() {
        User testUser = new User("Test User 1", "testEmail1@example.com");
        when(userRepository.existsByEmail("testEmail1@example.com")).thenReturn(true);

        assertThrows(ExistingUserException.class, ()-> userService.createUser(testUser));

        verify(userRepository, never()).save(any());
    }

    @Test
    void createUserShouldCreateIdAndSaveWhenEmailIsNew() {
        User testUser = new User("Test User 2", "testEmail2@example.com");
        when(userRepository.existsByEmail("testEmail2@example.com")).thenReturn(false);

        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser.getId());
        verify(userRepository).save(testUser);
    }
}