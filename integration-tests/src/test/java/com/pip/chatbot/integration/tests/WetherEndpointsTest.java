package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;

public class WetherEndpointsTest {

    @Karate.Test
    Karate weatherTests(){
        return Karate.run("./tests/weather").relativeTo(getClass());
    }

    @Karate.Test
    Karate test(){
        return Karate.run("./tests/weatherAdmin").relativeTo(getClass());
    }
}
