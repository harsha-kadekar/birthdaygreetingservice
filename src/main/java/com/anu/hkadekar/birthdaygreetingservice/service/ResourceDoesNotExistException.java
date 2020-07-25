package com.anu.hkadekar.birthdaygreetingservice.service;

public class ResourceDoesNotExistException extends RuntimeException {
    public ResourceDoesNotExistException(String message) {
        super(message);
    }
}
