package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;

public class AdminAuthorizationTest {

    @Karate.Test
    Karate test(){
        return Karate.run("./tests/adminAuthorization").relativeTo(getClass());
    }

    @Karate.Test
    Karate restaurationTests(){
        return Karate.run("./tests/restaurations").relativeTo(getClass());
    }
}
