package com.anu.hkadekar.birthdaygreetingservice.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UUID userId;
    private LocalDate birthDate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    private User(String firstName, String lastName, String email, String phoneNumber, UUID userId, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.birthDate = birthDate;
    }

    public static class Builder{
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private UUID userId;
        private LocalDate birthDate;

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastname(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
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

        public Builder withBirthDate(LocalDate date){
            this.birthDate = date;
            return this;
        }

        public User build(){
            return new User(firstName, lastName, email, phoneNumber, userId, birthDate);
        }
    }
}
