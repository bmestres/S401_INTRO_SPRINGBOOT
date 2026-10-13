package cat.itacademy.s04.t01.userapi.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAUsers_shouldReturnEmptyListInitially() throws Exception {
        mockMvc
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAUsers_withNameParameterShouldReturnFilteredUsers() {
    }

    @Test
    void addUser_shouldReturnUserWithId() {
    }

    @Test
    void getUserById_shouldReturnExpectedUser() {
    }

    @Test
    void getUserById_shouldReturnNotFoundIfMissing() {
    }
}