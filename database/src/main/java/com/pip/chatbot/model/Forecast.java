package com.pip.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class Forecast {
    @Getter @Setter public Integer id;
    @Getter @Setter public LocalDateTime createdOn;
    @Getter @Setter public LocalDateTime date;
    @Getter @Setter public float temperatureHigh;
    @Getter @Setter public float apparentTemperatureHigh;
    @Getter @Setter public float windSpeed;
    @Getter @Setter public float pressure;
    @Getter @Setter public float humidity;
    @Getter @Setter public String summary;
    @Getter @Setter public String precipType="Brak opadów";
    @Getter @Setter public String city;
}
