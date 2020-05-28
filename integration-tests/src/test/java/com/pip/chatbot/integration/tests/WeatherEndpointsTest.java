package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;

public class WeatherEndpointsTest {

    @Karate.Test
    Karate weatherTests(){
        return Karate.run("./weather/feature/weather").relativeTo(getClass());
    }

    @Karate.Test
    Karate test(){
        return Karate.run("./weather/feature/weather").relativeTo(getClass());
    }
}
