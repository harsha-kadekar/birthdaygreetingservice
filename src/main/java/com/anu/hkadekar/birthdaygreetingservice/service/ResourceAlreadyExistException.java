package com.anu.hkadekar.birthdaygreetingservice.service;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
