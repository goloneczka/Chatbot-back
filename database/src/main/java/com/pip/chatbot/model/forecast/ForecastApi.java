package com.pip.chatbot.model.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForecastApi {
    private Integer id;
    private float temperature;
    private float perceivedTemperature;
    private float windSpeed;
    private float pressure;
    private float humidity;
    private String summary;
    private String city;
    private String icon;
}
