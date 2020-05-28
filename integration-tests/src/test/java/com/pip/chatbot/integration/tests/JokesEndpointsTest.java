package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;

public class JokesEndpointsTest {
    @Karate.Test
    Karate adminJokesTest(){
        return Karate.run("./jokes/features/adminJokesEndpoints").relativeTo(getClass());
    }

    @Karate.Test
    Karate jokesTest(){
        return Karate.run("./jokes/features/jokesEndpoints").relativeTo(getClass());
    }

    @Karate.Test
    Karate categoriesTest(){
        return Karate.run("./jokes/features/categoriesEndpoints").relativeTo(getClass());
    }
}
