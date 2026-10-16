package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserController userController;

    void clearUserList() {

    }


   /* @BeforeEach
    void clearUserList() {
        userController.clearUserList();
    }*/

    @Test
    void getAUsers_shouldReturnEmptyListInitially() throws Exception {
        mockMvc
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void addUser_shouldReturnUserWithId() throws Exception {

        User user1 = new User("User1", "user1Email@email.com");
        String jsonUser1 = objectMapper.writeValueAsString(user1);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Matchers.notNullValue()));
    }

    @Test
    void getUserById_shouldReturnExpectedUser() throws Exception {

        User user2 = new User("User2", "user2Email@email.com");
        String jsonUser2 = objectMapper.writeValueAsString(user2);

        String createdUserResponse = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser2))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User createdUser = objectMapper.readValue(createdUserResponse, User.class);


        mockMvc.perform(get("/users/{id}", createdUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(Matchers.equalTo("User2")))
                .andExpect(jsonPath("$.email").value(Matchers.equalTo("user2Email@email.com")));
    }

    @Test
    void getUserById_shouldReturnNotFoundIfMissing() throws Exception {

        User user3 = new User("User3", "user3Email@email.com");
        String jsonUser3 = objectMapper.writeValueAsString(user3);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser3))
                .andExpect(status().isOk());

        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/users/{id}", randomId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAUsers_withNameParameterShouldReturnFilteredUsers() throws Exception {

        User user4 = new User("User4", "user4Email@email.com");
        String jsonUser4 = objectMapper.writeValueAsString(user4);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser4))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users").param("name", "User4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(Matchers.equalTo("User4")))
                .andExpect(jsonPath("$[0].email").value(Matchers.equalTo("user4Email@email.com")));
    }
}