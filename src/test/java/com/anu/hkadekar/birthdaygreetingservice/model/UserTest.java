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
                withLastName("test2").
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

    @Test
    void userEqualsTest() {
        UUID expectedUUID = UUID.randomUUID();
        User user1 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        User user2 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        Assertions.assertEquals(user1, user2);
    }


    @Test
    void userNotEqualsFirstNameTest() {
        UUID expectedUUID = UUID.randomUUID();
        User user1 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        User user2 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("Test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        Assertions.assertNotEquals(user1, user2);
    }

    @Test
    void userNotEqualsEmailTest() {
        UUID expectedUUID = UUID.randomUUID();
        User user1 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test2@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        User user2 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        Assertions.assertNotEquals(user1, user2);
    }

    @Test
    void userNotEqualsLastNameTest() {
        UUID expectedUUID = UUID.randomUUID();
        User user1 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        User user2 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2Other").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        Assertions.assertNotEquals(user1, user2);
    }

    @Test
    void userNotEqualsPhoneNumberTest() {
        UUID expectedUUID = UUID.randomUUID();
        User user1 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("0987654321").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        User user2 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2Other").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        Assertions.assertNotEquals(user1, user2);
    }

    @Test
    void userNotEqualsUUIDTest() {
        UUID expectedUUID = UUID.randomUUID();
        UUID anotherUUID = UUID.randomUUID();
        User user1 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        User user2 = new User.Builder().
                withUserId(anotherUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        Assertions.assertNotEquals(user1, user2);
    }


    @Test
    void userNotEqualsDateTest() {
        UUID expectedUUID = UUID.randomUUID();
        User user1 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1990, 10, 23)).
                build();

        User user2 = new User.Builder().
                withUserId(expectedUUID).
                withFirstName("test1").
                withLastName("test2").
                withEmail("test@test.com").
                withPhoneNumber("1234567890").
                withBirthDate(LocalDate.of(1992, 10, 23)).
                build();

        Assertions.assertNotEquals(user1, user2);
    }
}
