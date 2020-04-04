package com.pip.chatbot.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Forecast {
    public Integer id;
    public LocalDateTime createdOn;
    public LocalDateTime date;
    public float temperatureHigh;
    public float apparentTemperatureHigh;
    public float windSpeed;
    public float pressure;
    public float humidity;
    public String summary;
    public String precipType;
    public String city;
}
