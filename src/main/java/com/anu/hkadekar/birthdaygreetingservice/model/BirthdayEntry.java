package com.anu.hkadekar.birthdaygreetingservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;
import java.util.UUID;

@JsonDeserialize(builder = BirthdayEntry.Builder.class)
public class BirthdayEntry {
    private String email;
    private LocalDate birthDay;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UUID userId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    private BirthdayEntry(String email, LocalDate birthDay, String firstName, String lastName, String phoneNumber, UUID userId) {
        this.email = email;
        this.birthDay = birthDay;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        BirthdayEntry birthdayEntry = (BirthdayEntry) obj;

        if((birthdayEntry.getEmail() == null && email != null) || (birthdayEntry.getFirstName() == null && firstName != null) || (birthdayEntry.getLastName() == null && lastName != null) || (birthdayEntry.getPhoneNumber() == null && phoneNumber != null) || (birthdayEntry.getBirthDay() == null && birthDay != null) || (birthdayEntry.getUserId() == null && userId != null)) {
            return false;
        }

        if(!birthdayEntry.getEmail().equals(email)) {
            return false;
        }

        if(!birthdayEntry.getFirstName().equals(firstName)) {
            return false;
        }

        if(!birthdayEntry.getLastName().equals(lastName)) {
            return false;
        }

        if(!birthdayEntry.getPhoneNumber().equals(phoneNumber)) {
            return false;
        }

        if(!birthdayEntry.getBirthDay().equals(birthDay)) {
            return false;
        }

        if(!birthdayEntry.getUserId().equals(userId)) {
            return false;
        }

        return true;
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
    public static class Builder {
        private String email;
        private LocalDate birthDay;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private UUID userId;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withBirthDay(LocalDate birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withUserId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public BirthdayEntry build() {
            return new BirthdayEntry(email, birthDay, firstName, lastName, phoneNumber, userId);
        }


    }
}
