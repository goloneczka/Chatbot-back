package com.pip.chatbot.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkApi {


    private Integer restaurantId;
    private Double mark;
}
