package com.pip.chatbot.integration.tests.fortune;

import com.intuit.karate.junit5.Karate;

public class FortuneEndpointsTest {

    @Karate.Test
    Karate symbolTests(){
        return Karate.run("./feature/symbol.feature").relativeTo(getClass());
    }

    @Karate.Test
    Karate stockTests(){
        return Karate.run("./feature/stock.feature").relativeTo(getClass());
    }
}
