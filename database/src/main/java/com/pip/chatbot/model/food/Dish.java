package com.pip.chatbot.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dish {
    private int id;
    private String dish;
    private float price;
}
