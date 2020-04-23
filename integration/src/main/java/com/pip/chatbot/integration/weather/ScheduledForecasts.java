package com.pip.chatbot.integration.weather;

import com.pip.chatbot.model.forecast.Forecast;
import com.pip.chatbot.repository.forecast.CitiesRepository;
import com.pip.chatbot.repository.forecast.ForecastRepository;
import com.pip.chatbot.model.forecast.City;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableScheduling
@Component
public class ScheduledForecasts {
    private final DarkSkyApi darkSkyApi;
    private final ForecastRepository forecastRepository;
    private final CitiesRepository citiesRepository;
    private final ModelMapper modelMapper;

    public ScheduledForecasts(DarkSkyApi darkSkyApi, ForecastRepository forecastRepository, CitiesRepository citiesRepository, ModelMapper modelMapper) {
        this.darkSkyApi = darkSkyApi;
        this.forecastRepository = forecastRepository;
        this.citiesRepository = citiesRepository;
        this.modelMapper = modelMapper;
    }

    @Scheduled(fixedDelayString = "${application.darksky.forecastDelay}")
    public void saveForecasts() throws Exception {
        for (City city : citiesRepository.getAllCities()) {
            List<Forecast> forecasts = modelMapper.map(darkSkyApi.getWeatherForecast(city), new TypeToken<List<Forecast>>() {
            }.getType());
            forecastRepository.createForecasts(forecasts);
        }
    }
}
