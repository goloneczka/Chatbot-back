package com.pip.chatbot.integration.tests.weather;

import com.intuit.karate.junit5.Karate;

public class WeatherEndpointsTest {

    @Karate.Test
    Karate weatherTests(){
        return Karate.run("./feature/weather").relativeTo(getClass());
    }

    @Karate.Test
    Karate test(){
        return Karate.run("./feature/weatherAdmin").relativeTo(getClass());
    }
}
