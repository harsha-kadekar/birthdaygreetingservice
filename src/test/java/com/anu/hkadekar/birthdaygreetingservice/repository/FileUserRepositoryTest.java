package com.anu.hkadekar.birthdaygreetingservice.repository;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FileUserRepositoryTest {
    private static ObjectMapper objectMapper = null;
    private static final String userDataFilePath = "FileDB/UserDBFile.json";
    private static final String absolutePath = FileUserRepositoryTest.class.getClassLoader().getResource(userDataFilePath).getPath();

    @BeforeAll
    static void initialize() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @BeforeEach
    public void testInitializer() throws Exception{

        User user = new User.Builder().
                withFirstName("Harsha").
                withLastName("Kadekar").
                withEmail("harsha.kadekar@gmail.com").
                withPhoneNumber("0125439876").
                withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1988, 9, 7)).
                build();

        // Delete the Existing DB file
        File dbFile = new File(absolutePath);
        dbFile.delete();
        objectMapper.writeValue(Paths.get(absolutePath).toFile(), Arrays.asList(user));
    }

    @AfterEach
    public void testDestroyer() throws Exception{
        File dbFile = new File(absolutePath);
        dbFile.delete();
        List<User> emptyList = new ArrayList<>();
        objectMapper.writeValue(Paths.get(absolutePath).toFile(), Arrays.asList(emptyList));
    }

    @AfterEach
    public void resetSingleton() throws Exception{
        Field instance = FileUserRepository.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }


    @Test
    public void testGetAllUsers() throws Exception{

        User user = new User.Builder().
                withFirstName("Harsha").
                withLastName("Kadekar").
                withEmail("harsha.kadekar@gmail.com").
                withPhoneNumber("0125439876").
                withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1988, 9, 7)).
                build();

        FileUserRepository fileUserRepository = FileUserRepository.getInstance();
        List<User> actualUsers = fileUserRepository.getAllUsers();

        assertThat(actualUsers, hasItem(user));

    }

    @Test
    public void testAddUsers() throws Exception{
        List<User> users = new ArrayList<>();

        User user = new User.Builder().
                withFirstName("Anu").
                withLastName("Kadekar").
                withEmail("anu.kadekar@gmail.com").
                withPhoneNumber("1234567890").
                withUserId(UUID.fromString("f21191e6-a55b-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1992, 2, 26)).
                build();

        users.add(user);

        FileUserRepository fileUserRepository = FileUserRepository.getInstance();
        List<User> actualUsers = fileUserRepository.getAllUsers();

        Assertions.assertEquals(1, actualUsers.size());
        assertThat(actualUsers, not(hasItem(user)));

        fileUserRepository.addUsers(users);

        actualUsers = fileUserRepository.getAllUsers();

        Assertions.assertEquals(2, actualUsers.size());
        assertThat(actualUsers, hasItem(user));

    }

    @Test
    public void testGetUsersByEmail(){
        List<User> users = new ArrayList<>();

        User anuUser = new User.Builder().
                withFirstName("Anu").
                withLastName("Kadekar").
                withEmail("anu.kadekar@gmail.com").
                withPhoneNumber("1234567890").
                withUserId(UUID.fromString("f21191e6-a55b-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1992, 2, 26)).
                build();

        users.add(anuUser);

        FileUserRepository fileUserRepository = FileUserRepository.getInstance();
        User harshaUser = fileUserRepository.getAllUsers().get(0);
        fileUserRepository.addUsers(users);

        List<User> receivedUsers = fileUserRepository.getUsersByEmail(Arrays.asList("harsha.kadekar@gmail.com", "anu.kadekar@gmail.com"));

        Assertions.assertEquals(2, receivedUsers.size());
        assertThat(receivedUsers, hasItem(anuUser));
        assertThat(receivedUsers, hasItem(harshaUser));

        receivedUsers = fileUserRepository.getUsersByEmail(Arrays.asList("anu.kadekar@gmail.com"));
        Assertions.assertEquals(1, receivedUsers.size());
        assertThat(receivedUsers, hasItem(anuUser));

        receivedUsers = fileUserRepository.getUsersByEmail(Arrays.asList("ex1.ex1@ex1.com"));
        Assertions.assertEquals(0, receivedUsers.size());
    }

    @Test
    public void testGetUsersById(){
        List<User> users = new ArrayList<>();
        UUID harshaID = UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002");
        UUID anuID = UUID.fromString("f21191e6-a55b-11ea-bb37-0242ac130002");

        User anuUser = new User.Builder().
                withFirstName("Anu").
                withLastName("Kadekar").
                withEmail("anu.kadekar@gmail.com").
                withPhoneNumber("1234567890").
                withUserId(anuID).
                withBirthDate(LocalDate.of(1992, 2, 26)).
                build();

        users.add(anuUser);

        FileUserRepository fileUserRepository = FileUserRepository.getInstance();
        User harshaUser = fileUserRepository.getAllUsers().get(0);
        fileUserRepository.addUsers(users);

        List<User> receivedUsers = fileUserRepository.getUsersById(Arrays.asList(harshaID, anuID));

        Assertions.assertEquals(2, receivedUsers.size());
        assertThat(receivedUsers, hasItem(anuUser));
        assertThat(receivedUsers, hasItem(harshaUser));

        receivedUsers = fileUserRepository.getUsersById(Arrays.asList(anuID));
        Assertions.assertEquals(1, receivedUsers.size());
        assertThat(receivedUsers, hasItem(anuUser));

        receivedUsers = fileUserRepository.getUsersById(Arrays.asList(UUID.randomUUID()));
        Assertions.assertEquals(0, receivedUsers.size());
    }

    @Test
    public void testRemoveUsers(){
        List<User> users = new ArrayList<>();

        User anuUser = new User.Builder().
                withFirstName("Anu").
                withLastName("Kadekar").
                withEmail("anu.kadekar@gmail.com").
                withPhoneNumber("1234567890").
                withUserId(UUID.fromString("f21191e6-a55b-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1992, 2, 26)).
                build();

        users.add(anuUser);

        FileUserRepository fileUserRepository = FileUserRepository.getInstance();
        List<User> actualUsers = fileUserRepository.getAllUsers();

        Assertions.assertEquals(1, actualUsers.size());

        User harshaUser = actualUsers.get(0);
        users.add(harshaUser);

        fileUserRepository.removeUsers(actualUsers);
        actualUsers = fileUserRepository.getAllUsers();
        Assertions.assertEquals(0, actualUsers.size());

        fileUserRepository.addUsers(users);
        actualUsers = fileUserRepository.getAllUsers();
        Assertions.assertEquals(2, actualUsers.size());

        fileUserRepository.removeUsers(users);
        actualUsers = fileUserRepository.getAllUsers();
        Assertions.assertEquals(0, actualUsers.size());

        fileUserRepository.addUsers(users);
        actualUsers = fileUserRepository.getAllUsers();
        Assertions.assertEquals(2, actualUsers.size());
        fileUserRepository.removeUsers(Arrays.asList(harshaUser));
        actualUsers = fileUserRepository.getAllUsers();
        Assertions.assertEquals(1, actualUsers.size());
        assertThat(actualUsers, not(hasItem(harshaUser)));

        actualUsers = fileUserRepository.getAllUsers();
        Assertions.assertEquals(1, actualUsers.size());
        fileUserRepository.removeUsers(Arrays.asList(harshaUser));
        actualUsers = fileUserRepository.getAllUsers();
        Assertions.assertEquals(1, actualUsers.size());
        assertThat(actualUsers, not(hasItem(harshaUser)));

    }
}
