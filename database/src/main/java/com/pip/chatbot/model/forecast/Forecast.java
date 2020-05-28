package com.pip.chatbot.model.forecast;

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
    private String city;
    private String icon;
}
