package com.pip.chatbot.integration.tests.jokes;

import com.intuit.karate.junit5.Karate;

public class JokesEndpointsTest {
    @Karate.Test
    Karate adminJokesTest(){
        return Karate.run("./features/adminJokesEndpoints").relativeTo(getClass());
    }

    @Karate.Test
    Karate jokesTest(){
        return Karate.run("./features/jokesEndpoints").relativeTo(getClass());
    }

    @Karate.Test
    Karate categoriesTest(){
        return Karate.run("./features/categoriesEndpoints").relativeTo(getClass());
    }
}
