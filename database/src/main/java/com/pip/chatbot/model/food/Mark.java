package com.pip.chatbot.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mark {

    private Integer id;
    private Integer restaurantId;
    private Float mark;
}
