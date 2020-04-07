package com.pip.chatbot.integrationApplication.tests;
import com.intuit.karate.junit5.Karate;

class SampleTest {
    @Karate.Test
    Karate testSample() {
        return Karate.run().relativeTo(getClass());
    }
}

