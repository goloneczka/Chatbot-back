package com.pip.chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {


    @GetMapping("/admin/login")
    public String getAdmin() {
        return "I wanna be adminos.";
    }

    @GetMapping("/admin/hello")
    public String getAdminString() {
        return "Im adminos, hello";
    }

    @GetMapping("/hello")
    public String getNoAdminString() {
        return "Im not admin, hello";
    }
}
