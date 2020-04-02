package com.pip.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Getter
    @Setter
    public String city;
    @Getter
    @Setter
    public float latitude;
    @Getter
    @Setter
    public float longitude;
    @Getter
    @Setter
    public String country;
}
