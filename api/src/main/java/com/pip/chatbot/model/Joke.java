package com.pip.chatbot.model;

import lombok.Data;

@Data
public class Joke {

    private Long id;
    private String joke;
    private String category;

    public Joke() {
    }
}
