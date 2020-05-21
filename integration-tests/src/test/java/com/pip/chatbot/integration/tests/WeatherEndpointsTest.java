package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;

public class WeatherEndpointsTest {

    @Karate.Test
    Karate weatherTests(){
        return Karate.run("./tests/weather/weather").relativeTo(getClass());
    }

    @Karate.Test
    Karate test(){
        return Karate.run("./tests/weather/weatherAdmin").relativeTo(getClass());
    }
}
