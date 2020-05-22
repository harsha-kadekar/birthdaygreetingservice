package com.anu.hkadekar.birthdaygreetingservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class UserTest {

    @Test
    void createUserTest() {
        UUID expectedUUID = UUID.randomUUID();
        User user = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastname("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        Assertions.assertEquals("test1", user.getFirstName());
        Assertions.assertEquals("test2", user.getLastName());
        Assertions.assertEquals("test@test.com", user.getEmail());
        Assertions.assertEquals("1234567890", user.getPhoneNumber());
        Assertions.assertEquals(LocalDate.of(1990, 10, 23).toString(), user.getBirthDate().toString());
        Assertions.assertEquals(expectedUUID, user.getUserId());

    }
}
