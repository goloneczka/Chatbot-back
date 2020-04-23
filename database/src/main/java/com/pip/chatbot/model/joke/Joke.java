package com.pip.chatbot.model.joke;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Joke {

    private Integer id;
    private String joke;
    private String category;
    private boolean isConfirmed;

}
