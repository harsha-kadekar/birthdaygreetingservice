package com.anu.hkadekar.birthdaygreetingservice.repository;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class FileUserRepositoryTest {

    @Test
    public void testGetAllUsers() throws Exception{

        User user = new User.Builder().
                withFirstName("Harsha").
                withLastName("Kadekar").
                withEmail("harsha.kadekar@gmail.com").
                withPhoneNumber("2028132363").
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
}
