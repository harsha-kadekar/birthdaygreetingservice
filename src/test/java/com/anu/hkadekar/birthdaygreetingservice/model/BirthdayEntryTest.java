package com.anu.hkadekar.birthdaygreetingservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class BirthdayEntryTest {

    @Test
    public void createBirthdayEntryTest() {
        UUID userId = UUID.randomUUID();
        BirthdayEntry birthdayEntry = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        Assertions.assertEquals(birthdayEntry.getEmail(), "abc@abc.com");
        Assertions.assertEquals(birthdayEntry.getFirstName(), "harsha");
        Assertions.assertEquals(birthdayEntry.getLastName(), "kadekar");
        Assertions.assertEquals(birthdayEntry.getPhoneNumber(), "1234567890");
        Assertions.assertEquals(birthdayEntry.getUserId(), userId);
        Assertions.assertEquals(birthdayEntry.getBirthDay(), LocalDate.of(1988, 9, 7));
    }

    @Test
    public void birthdayEntryEqualsTest() {
        UUID userId = UUID.randomUUID();
        BirthdayEntry birthdayEntry1 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        BirthdayEntry birthdayEntry2 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        Assertions.assertEquals(birthdayEntry1, birthdayEntry2);

    }

    @Test
    public void birthdayEntryEmailNotEqualsTest() {
        UUID userId = UUID.randomUUID();
        BirthdayEntry birthdayEntry1 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        BirthdayEntry birthdayEntry2 = new BirthdayEntry.Builder()
                .withEmail("another@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        Assertions.assertNotEquals(birthdayEntry1, birthdayEntry2);
    }

    @Test
    public void birthdayEntryFirstNameNotEqualsTest() {
        UUID userId = UUID.randomUUID();
        BirthdayEntry birthdayEntry1 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        BirthdayEntry birthdayEntry2 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("Another")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        Assertions.assertNotEquals(birthdayEntry1, birthdayEntry2);
    }

    @Test
    public void birthdayEntryLastNameNotEqualsTest() {
        UUID userId = UUID.randomUUID();
        BirthdayEntry birthdayEntry1 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        BirthdayEntry birthdayEntry2 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("LNU")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        Assertions.assertNotEquals(birthdayEntry1, birthdayEntry2);
    }

    @Test
    public void birthdayEntryPhoneNumberNotEqualsTest() {
        UUID userId = UUID.randomUUID();
        BirthdayEntry birthdayEntry1 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        BirthdayEntry birthdayEntry2 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("9876543210")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        Assertions.assertNotEquals(birthdayEntry1, birthdayEntry2);
    }

    @Test
    public void birthdayEntryUserIdNotEqualsTest() {
        UUID userId = UUID.randomUUID();
        BirthdayEntry birthdayEntry1 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        BirthdayEntry birthdayEntry2 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(UUID.randomUUID())
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        Assertions.assertNotEquals(birthdayEntry1, birthdayEntry2);
    }

    @Test
    public void birthdayEntryBirthDateNotEqualsTest() {
        UUID userId = UUID.randomUUID();
        BirthdayEntry birthdayEntry1 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 9, 7))
                .build();

        BirthdayEntry birthdayEntry2 = new BirthdayEntry.Builder()
                .withEmail("abc@abc.com")
                .withFirstName("harsha")
                .withLastName("kadekar")
                .withPhoneNumber("1234567890")
                .withUserId(userId)
                .withBirthDay(LocalDate.of(1988, 10, 7))
                .build();

        Assertions.assertNotEquals(birthdayEntry1, birthdayEntry2);
    }
}
