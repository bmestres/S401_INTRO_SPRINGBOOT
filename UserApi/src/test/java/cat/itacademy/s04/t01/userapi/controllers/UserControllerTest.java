package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.UUID;
import java.util.regex.Matcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void addUser_shouldReturnUserWithId() throws Exception {

        User user1 = new User(null, "User1", "user1Email@email.com");

        String jsonUser1 = objectMapper.writeValueAsString(user1);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Matchers.notNullValue()));
    }

    @Test
    void getUserById_shouldReturnExpectedUser() {
    }

    @Test
    void getUserById_shouldReturnNotFoundIfMissing() {
    }

    @Test
    void getAUsers_withNameParameterShouldReturnFilteredUsers() {
    }
}