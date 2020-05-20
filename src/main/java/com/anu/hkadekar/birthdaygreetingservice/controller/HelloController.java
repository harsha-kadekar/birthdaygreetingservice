package com.anu.hkadekar.birthdaygreetingservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Hello! Welcome to Birthday Greetings Service!";
    }
}
