package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;

public class FoodEndpointsTest {
    @Karate.Test
    Karate foodTests(){
        return Karate.run("./food/feature/food").relativeTo(getClass());
    }

}
