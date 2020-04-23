package com.pip.chatbot.model;

import lombok.Data;

@Data
public class Joke {
    private int id;
    private String category;
    private String joke;
}