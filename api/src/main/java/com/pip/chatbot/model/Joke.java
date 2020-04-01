package com.pip.chatbot.model;

import lombok.Getter;
import lombok.Setter;
public class Joke {


    @Getter @Setter private Long id;
    @Getter @Setter private String joke;

    @Getter @Setter private String category;

    public Joke() {
    }
}
