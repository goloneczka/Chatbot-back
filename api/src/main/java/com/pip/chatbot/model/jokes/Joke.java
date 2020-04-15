package com.pip.chatbot.model.jokes;

import lombok.Data;

@Data
public class Joke {
    int id;
    String category;
    String joke;
}