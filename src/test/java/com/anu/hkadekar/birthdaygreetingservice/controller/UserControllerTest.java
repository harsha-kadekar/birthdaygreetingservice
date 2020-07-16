package com.anu.hkadekar.birthdaygreetingservice.controller;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import com.anu.hkadekar.birthdaygreetingservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UserService userService;

    @Test
    public void testGetUsers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));

        List<User> users = new ArrayList<>();

        User harshaUser = new User.Builder()
                .withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"))
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withBirthDate(LocalDate.of(1988, 9, 7))
                .build();
        String jsonUsers = "[{\"firstName\":\"harsha\",\"lastName\":\"kadekar\",\"email\":\"abc@abc.com\",\"phoneNumber\":\"1234567890\",\"userId\":\"2ea330f4-9ca8-11ea-bb37-0242ac130002\",\"birthDate\":\"1988-09-07\"}]";

        users.add(harshaUser);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(jsonUsers)));

    }

    @Test
    public void testAddUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));

        UUID randomUUID = UUID.randomUUID();

        String jsonInputUser = "{\"firstName\":\"harsha\",\"lastName\":\"kadekar\",\"email\":\"abc@abc.com\",\"phoneNumber\":\"1234567890\",\"birthDate\":\"1988-09-07\"}";


        User harshaUserWithoutUUID = new User.Builder().withFirstName("harsha").withLastName("kadekar").withEmail("abc@abc.com").withPhoneNumber("1234567890").withBirthDate(LocalDate.of(1988, 9, 7)).build();
        User harshaUserWithUUID = new User.Builder().withFirstName("harsha").withLastName("kadekar").withEmail("abc@abc.com").withPhoneNumber("1234567890").withBirthDate(LocalDate.of(1988, 9, 7)).withUserId(randomUUID).build();

        doReturn(harshaUserWithUUID).when(userService).addUser(harshaUserWithoutUUID);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputUser))
                .andExpect(jsonPath("$.firstName", is("harsha")))
                .andExpect(jsonPath("$.lastName", is("kadekar")))
                .andExpect(jsonPath("$.email", is("abc@abc.com")))
                .andExpect(jsonPath("$.phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$.birthDate", is("1988-09-07")))
                .andExpect(jsonPath("$.userId", is(randomUUID.toString())));




    }



}
