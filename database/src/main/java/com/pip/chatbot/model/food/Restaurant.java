package com.pip.chatbot.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restaurant {
    private Integer id;
    private String name;
    private String address;
    private Integer city_id;
    private Double average_users_rating;
    private String phone_numbers;
}
