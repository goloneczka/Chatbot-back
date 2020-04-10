package com.pip.chatbot.service;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.CitiesErrorMessages;
import com.pip.chatbot.exception.messages.ForecastsErrorMessages;
import com.pip.chatbot.model.Forecast;
import com.pip.chatbot.repository.CitiesRepository;
import com.pip.chatbot.repository.ForecastRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ForecastsService {
    private final ForecastRepository forecastRepository;

    public ForecastsService(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    public Forecast getForecast(String city, LocalDateTime dateTime) {
        return forecastRepository.getForecastsForCityAndDate(city, dateTime)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(ForecastsErrorMessages.NOT_FOUND).build());
    }
}
