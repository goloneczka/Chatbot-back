package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;

public class IntegrationTest {

    @Karate.Test
    Karate testAll() {
        return Karate.run().relativeTo(getClass());
    }
}

