package com.pip.chatbot.service.forecast;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.ForecastsErrorMessages;
import com.pip.chatbot.model.forecast.Forecast;
import com.pip.chatbot.repository.forecast.ForecastRepository;
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
