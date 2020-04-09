package com.pip.chatbot.controller;

import com.pip.chatbot.model.Forecast;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.ForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class ForecastsController {
    private final ForecastService forecastService;

    public ForecastsController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("forecasts/city/{city}")
    public ResponseEntity<Forecast> getForecastsForCity(@PathVariable String city, @RequestParam String date) {
        LocalDate dateParsed = LocalDate.parse(date);
        return ResponseEntity.status(ResponseStatus.OK).body(forecastService.getForecast(city, dateParsed.atStartOfDay()));
    }
}
