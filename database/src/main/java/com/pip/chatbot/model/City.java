package com.pip.chatbot.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class City {
    public String city;
    public float latitude;
    public float longitude;
    public String country;
}
