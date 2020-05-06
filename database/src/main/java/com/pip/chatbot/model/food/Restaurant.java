package com.pip.chatbot.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restaurant {
    private Integer id;
    private String name;
    private String address;
    private Integer cityId;
    private Double averageUsersRating;
    private String phoneNumbers;

}
