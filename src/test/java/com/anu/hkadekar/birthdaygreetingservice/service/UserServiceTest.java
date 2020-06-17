package com.anu.hkadekar.birthdaygreetingservice.service;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import com.anu.hkadekar.birthdaygreetingservice.repository.FileUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class UserServiceTest {
    private static ObjectMapper objectMapper = null;
    private static final String userDataFilePath = "FileDB/UserDBFile.json";
    private static final String absolutePath = UserServiceTest.class.getClassLoader().getResource(userDataFilePath).getPath();

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
    public void testAddUser() {
        User anuUser = new User.Builder().
                withFirstName("Anu").
                withLastName("Kadekar").
                withEmail("anu.kadekar@gmail.com").
                withPhoneNumber("1234567890").
                withUserId(UUID.fromString("f21191e6-a55b-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1992, 2, 26)).
                build();

        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();
        Assertions.assertEquals(1, users.size());
        assertThat(users, not(hasItem(anuUser)));
        userService.addUser(anuUser);
        users = userService.getAllUsers();
        Assertions.assertEquals(2, users.size());
        assertThat(users, hasItem(anuUser));
    }

    @Test
    public void testAddExistingUser() {
        User user = new User.Builder().
                withFirstName("Harsha").
                withLastName("Kadekar").
                withEmail("harsha.kadekar@gmail.com").
                withPhoneNumber("0125439876").
                withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1988, 9, 7)).
                build();

        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();
        assertThat(users, hasItem(user));

        Exception exception = Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
            userService.addUser(user);
        });

        String expectedMessage = String.format("User with email %s already exists", user.getEmail());
        Assertions.assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    public void testFindUserByEmail() {
        User user = new User.Builder().
                withFirstName("Harsha").
                withLastName("Kadekar").
                withEmail("harsha.kadekar@gmail.com").
                withPhoneNumber("0125439876").
                withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1988, 9, 7)).
                build();

        UserService userService = new UserService();
        User searchedUser = userService.findUserByEmail(user.getEmail());
        Assertions.assertEquals(user, searchedUser);
    }

    @Test
    public void testFindUserByEmailNotExists() {
        UserService userService = new UserService();
        User searchedUser = userService.findUserByEmail("xyz@xyz.com");
        Assertions.assertNull(searchedUser);
    }

    @Test
    public void testFindUserById() {
        User user = new User.Builder().
                withFirstName("Harsha").
                withLastName("Kadekar").
                withEmail("harsha.kadekar@gmail.com").
                withPhoneNumber("0125439876").
                withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1988, 9, 7)).
                build();

        UserService userService = new UserService();
        User searchedUser = userService.findUserById(user.getUserId());
        Assertions.assertEquals(user, searchedUser);
    }

    @Test
    public void testFindUserByIdNotExists() {
        UserService userService = new UserService();
        User searchedUser = userService.findUserById(UUID.fromString("f21191e6-a55b-11ea-bb37-0242ac130002"));
        Assertions.assertNull(searchedUser);

    }

    @Test
    public void testRemoveUser() {
        User user = new User.Builder().
                withFirstName("Harsha").
                withLastName("Kadekar").
                withEmail("harsha.kadekar@gmail.com").
                withPhoneNumber("0125439876").
                withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1988, 9, 7)).
                build();

        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();
        assertThat(users, hasItem(user));
        Assertions.assertEquals(1, users.size());

        userService.removeUser(user);
        users = userService.getAllUsers();
        assertThat(users, not(hasItem(user)));
        Assertions.assertEquals(0, users.size());

    }

    @Test
    public void testRemoveUserNotExists() {
        User anuUser = new User.Builder().
                withFirstName("Anu").
                withLastName("Kadekar").
                withEmail("anu.kadekar@gmail.com").
                withPhoneNumber("1234567890").
                withUserId(UUID.fromString("f21191e6-a55b-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1992, 2, 26)).
                build();

        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();
        assertThat(users, not(hasItem(anuUser)));
        Assertions.assertEquals(1, users.size());

        userService.removeUser(anuUser);
        users = userService.getAllUsers();
        assertThat(users, not(hasItem(anuUser)));
        Assertions.assertEquals(1, users.size());

    }

    @Test
    public void testGetAllUsers() {
        User user = new User.Builder().
                withFirstName("Harsha").
                withLastName("Kadekar").
                withEmail("harsha.kadekar@gmail.com").
                withPhoneNumber("0125439876").
                withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1988, 9, 7)).
                build();

        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();
        assertThat(users, hasItem(user));
        Assertions.assertEquals(1, users.size());

    }
}
