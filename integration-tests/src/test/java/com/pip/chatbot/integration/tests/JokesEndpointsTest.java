package com.pip.chatbot.integration.tests;

import com.intuit.karate.junit5.Karate;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.repository.joke.CategoriesRepository;

public class JokesEndpointsTest {
    @Karate.Test
    Karate test(){
        return Karate.run("./tests/jokesEndpoints").relativeTo(getClass());
    }
}
