package com.pip.chatbot.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Forecast {
    private Integer id;
    private LocalDateTime createdOn;
    private LocalDateTime date;
    private float temperature;
    private float perceivedTemperature;
    private float windSpeed;
    private float pressure;
    private float humidity;
    private String summary;
    private String precipType;
    private String city;
}
