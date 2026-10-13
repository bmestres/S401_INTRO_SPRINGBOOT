package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

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
    void getUserById_shouldReturnExpectedUser() throws Exception {

        User user2 = new User(null, "User2", "user1Email@email.com");
        String jsonUser2 = objectMapper.writeValueAsString(user2);

        String createdUserResponse = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser2))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User createdUser = objectMapper.readValue(createdUserResponse, User.class);


        mockMvc.perform(get("/users/{id}", createdUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(Matchers.equalTo("User2")))
                .andExpect(jsonPath("$.email").value(Matchers.equalTo("user1Email@email.com")));
    }

    @Test
    void getUserById_shouldReturnNotFoundIfMissing() {
    }

    @Test
    void getAUsers_withNameParameterShouldReturnFilteredUsers() {
    }
}