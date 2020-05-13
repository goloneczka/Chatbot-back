package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;

public class CategoriesEndpointsTest {
    @Karate.Test
    Karate test(){
        return Karate.run("./tests/categoriesEndpoints").relativeTo(getClass());
    }
}
