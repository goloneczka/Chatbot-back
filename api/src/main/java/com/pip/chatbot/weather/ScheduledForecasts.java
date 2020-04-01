package com.pip.chatbot.weather;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.dao.CitiesDao;
import com.pip.chatbot.dao.ForecastDao;
import com.pip.chatbot.integration.weather.DarkSkyApi;
import com.pip.chatbot.jooq.weather.tables.records.CityRecord;
import com.pip.chatbot.model.Forecast;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
public class ScheduledForecasts {

    @Scheduled(fixedDelay = 86400000)
    public void saveForecastToDb(){
        try {
            CitiesDao citiesDao = new CitiesDao();
            ForecastDao forecastDao = new ForecastDao();
            DarkSkyApi darkSkyApi = new DarkSkyApi();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            for (CityRecord cityRecord : citiesDao.getAllCities()) {
                String forecastDaysJson = darkSkyApi.getWeatherForecast(cityRecord.getLatitude(), cityRecord.getLongitude());
                System.out.println(forecastDaysJson);
                List<Forecast> forecasts = objectMapper.readValue(forecastDaysJson, new TypeReference<List<Forecast>>() {});

                for (int i = 0; i < forecasts.size(); i++) {
                    forecasts.get(i).setCreatedOn(LocalDateTime.now());
                    forecasts.get(i).setDate(LocalDateTime.now().plusDays(i));
                    forecasts.get(i).setCity(cityRecord.getCity());
                    forecastDao.createForecast(forecasts.get(i));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
