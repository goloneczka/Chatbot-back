package com.pip.chatbot.integration.weather;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.dao.CitiesDao;
import com.pip.chatbot.dao.ForecastDao;
import com.pip.chatbot.model.City;
import com.pip.chatbot.model.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@Component
public class ScheduledForecasts {
    @Autowired
    DarkSkyApi darkSkyApi;
    @Autowired
    ForecastDao forecastDao;
    @Autowired
    CitiesDao citiesDao;

    @Scheduled(fixedDelayString = "${application.darksky.forecastDelay}")
    public void saveForecastToDb() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            for (City city : citiesDao.getAllCities()) {
                String forecastDaysJson = darkSkyApi.getWeatherForecast(city.getLatitude(), city.getLongitude());
                List<Forecast> forecasts = objectMapper.readValue(forecastDaysJson, new TypeReference<List<Forecast>>() {
                });

                for (int i = 0; i < forecasts.size(); i++) {
                    forecasts.get(i).setCreatedOn(LocalDateTime.now());
                    forecasts.get(i).setDate(LocalDateTime.now().plusDays(i));
                    forecasts.get(i).setCity(city.getCity());
                    forecastDao.createForecast(forecasts.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
