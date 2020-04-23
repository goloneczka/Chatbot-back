package com.pip.chatbot.model.joke;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mark {

    private Integer id;
    private Integer jokeId;
    private Double mark;
}
