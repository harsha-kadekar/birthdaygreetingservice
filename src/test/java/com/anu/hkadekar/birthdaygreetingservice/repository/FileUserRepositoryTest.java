package com.anu.hkadekar.birthdaygreetingservice.repository;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class FileUserRepositoryTest {

    @Test
    public void testGetAllUsers() throws Exception{

        List<User> expectedUsers = new ArrayList<>();
        User user = new User.Builder().
                withFirstName("Harsha").
                withLastName("Kadekar").
                withEmail("harsha.kadekar@gmail.com").
                withPhoneNumber("2028132363").
                withUserId(UUID.fromString("2ea330f4-9ca8-11ea-bb37-0242ac130002")).
                withBirthDate(LocalDate.of(1988, 9, 7)).
                build();

        expectedUsers.add(user);

        FileUserRepository fileUserRepository = FileUserRepository.getInstance();
        List<User> actualUsers = fileUserRepository.getAllUsers();

        assertThat(actualUsers, hasItem(user));

    }
}
