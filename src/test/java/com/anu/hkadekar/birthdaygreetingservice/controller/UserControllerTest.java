package com.anu.hkadekar.birthdaygreetingservice.controller;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import com.anu.hkadekar.birthdaygreetingservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
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

    @Test
    public void testGetUserById() throws Exception {
        User harshaUser = new User.Builder()
                .withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"))
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withBirthDate(LocalDate.of(1988, 9, 7))
                .build();

        doReturn(harshaUser).when(userService).findUserById(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/2ea330f4-9ca8-11ea-bb37-0242ac130002")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("harsha")))
                .andExpect(jsonPath("$.lastName", is("kadekar")))
                .andExpect(jsonPath("$.email", is("abc@abc.com")))
                .andExpect(jsonPath("$.phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$.birthDate", is("1988-09-07")))
                .andExpect(jsonPath("$.userId", is("2ea330f4-9ca8-11ea-bb37-0242ac130002")));
    }

    @Test
    public void testFailedGetUserById() throws Exception {
        doReturn(null)
                .when(userService)
                .findUserById(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/2ea330f4-9ca8-11ea-bb37-0242ac130002")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("User with user Id 2ea330f4-9ca8-11ea-bb37-0242ac130002 is not found" ));
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        User harshaUser = new User.Builder()
                .withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"))
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withBirthDate(LocalDate.of(1988, 9, 7))
                .build();

        doReturn(harshaUser).when(userService).findUserByEmail("abc@abc.com");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/abc@abc.com")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("harsha")))
                .andExpect(jsonPath("$.lastName", is("kadekar")))
                .andExpect(jsonPath("$.email", is("abc@abc.com")))
                .andExpect(jsonPath("$.phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$.birthDate", is("1988-09-07")))
                .andExpect(jsonPath("$.userId", is("2ea330f4-9ca8-11ea-bb37-0242ac130002")));

    }

    @Test
    public void testFailedGetUserByEmail() throws Exception {
        doReturn(null)
                .when(userService)
                .findUserByEmail("abc@abc.com");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/abc@abc.om")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("User with email abc@abc.om is not found" ));
    }

    @Test
    public void testRemoveUserByEmail() throws Exception {
        User harshaUser = new User.Builder()
                .withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"))
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withBirthDate(LocalDate.of(1988, 9, 7))
                .build();

        doReturn(harshaUser).when(userService).findUserByEmail("abc@abc.com");
        doNothing().when(userService).removeUser(harshaUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user/abc@abc.com")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).findUserByEmail("abc@abc.com");
        verify(userService).removeUser(harshaUser);
    }

    @Test
    public void testFailedRemoveUserByEmail() throws Exception {

        doReturn(null).when(userService).findUserByEmail("abc@abc.com");

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user/abc@abc.com")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("User with email abc@abc.com is not found" ));

        verify(userService).findUserByEmail("abc@abc.com");
        verify(userService, never()).removeUser(Mockito.any());
    }

    @Test
    public void testRemoveUserById() throws Exception {
        User harshaUser = new User.Builder()
                .withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"))
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withBirthDate(LocalDate.of(1988, 9, 7))
                .build();

        doReturn(harshaUser).when(userService).findUserById(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"));
        doNothing().when(userService).removeUser(harshaUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user/2ea330f4-9ca8-11ea-bb37-0242ac130002")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).findUserById(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"));
        verify(userService).removeUser(harshaUser);
    }

    @Test
    public void testFailedRemoveUserById() throws Exception {

        doReturn(null).when(userService).findUserById(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user/2ea330f4-9ca8-11ea-bb37-0242ac130002")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason("User with user Id 2ea330f4-9ca8-11ea-bb37-0242ac130002 is not found" ));

        verify(userService).findUserById(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002"));
        verify(userService, never()).removeUser(Mockito.any());
    }

}
