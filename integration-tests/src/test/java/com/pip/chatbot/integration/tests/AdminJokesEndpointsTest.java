package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;

public class AdminJokesEndpointsTest {
    @Karate.Test
    Karate test(){
        return Karate.run("./tests/adminJokesEndpoints").relativeTo(getClass());
    }
}
