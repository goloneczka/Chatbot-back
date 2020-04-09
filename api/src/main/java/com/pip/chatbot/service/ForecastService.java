package com.pip.chatbot.service;

import com.pip.chatbot.Exception.ChatbotExceptionBuilder;
import com.pip.chatbot.Exception.messages.CitiesErrorMessages;
import com.pip.chatbot.Exception.messages.ForecastsErrorMessages;
import com.pip.chatbot.model.Forecast;
import com.pip.chatbot.repository.CitiesRepository;
import com.pip.chatbot.repository.ForecastRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ForecastService {
    private final ForecastRepository forecastRepository;
    private final CitiesRepository citiesRepository;

    public ForecastService(ForecastRepository forecastRepository, CitiesRepository citiesRepository) {
        this.forecastRepository = forecastRepository;
        this.citiesRepository = citiesRepository;
    }

    public Forecast getForecast(String city, LocalDateTime dateTime) {
        if (!citiesRepository.isCityExist(city)) {
            throw new ChatbotExceptionBuilder().addError(CitiesErrorMessages.NOT_FOUND).build();
        }

        Optional<Forecast> forecast = forecastRepository.getForecastsForCityAndDate(city, dateTime);

        if (forecast.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(ForecastsErrorMessages.NOT_FOUND).build();
        }
        return forecast.get();
    }
}
