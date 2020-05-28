package com.pip.chatbot.integration.tests.admin;

import com.intuit.karate.junit5.Karate;

public class AdminAuthorizationTest {

    @Karate.Test
    Karate test(){
        return Karate.run("./feature/adminAuthorization").relativeTo(getClass());
    }

}
