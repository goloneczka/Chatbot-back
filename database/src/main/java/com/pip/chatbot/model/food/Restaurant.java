package com.pip.chatbot.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restaurant {
    private int id;
    private String name;
    private String address;
    private int cityId;
    private float averageUsersRating;
    private String phoneNumbers;
    private List<Cuisine> cuisines;
}
