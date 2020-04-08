package com.pip.chatbot.controller;

import com.intuit.karate.junit5.Karate;

public class AdminControllerTest {

    @Karate.Test
    Karate test(){
        return Karate.run("./tests/adminController").relativeTo(getClass());
    }
}
