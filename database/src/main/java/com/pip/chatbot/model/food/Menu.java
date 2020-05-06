package com.pip.chatbot.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Menu {
    private int id;
    private int restaurantId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Dish> dishes;
}
