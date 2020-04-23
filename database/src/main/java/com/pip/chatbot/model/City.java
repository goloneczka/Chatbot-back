package com.pip.chatbot.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class City {
    private String city;
    private float latitude;
    private float longitude;
    private String country;
}
