package com.pip.chatbot.integration.weather;

import com.pip.chatbot.repository.CitiesRepository;
import com.pip.chatbot.repository.ForecastRepository;
import com.pip.chatbot.model.City;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class ScheduledForecasts {
    private final DarkSkyApi darkSkyApi;
    private final ForecastRepository forecastRepository;
    private final CitiesRepository citiesRepository;

    public ScheduledForecasts(DarkSkyApi darkSkyApi, ForecastRepository forecastRepository, CitiesRepository citiesRepository) {
        this.darkSkyApi = darkSkyApi;
        this.forecastRepository = forecastRepository;
        this.citiesRepository = citiesRepository;
    }

    @Scheduled(fixedDelayString = "${application.darksky.forecastDelay}")
    public void saveForecasts() throws Exception {
        for (City city : citiesRepository.getAllCities()) {
            forecastRepository.createForecasts(darkSkyApi.getWeatherForecast(city));
        }
    }
}
