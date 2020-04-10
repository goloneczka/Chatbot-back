package com.pip.chatbot.controller;

import com.pip.chatbot.model.Forecast;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.ForecastsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class ForecastsController {
    private final ForecastsService forecastsService;

    public ForecastsController(ForecastsService forecastsService) {
        this.forecastsService = forecastsService;
    }

    @GetMapping("forecasts/city/{city}")
    public ResponseEntity<Forecast> getForecastsForCity(@PathVariable String city, @RequestParam String date) {
        LocalDate dateParsed = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return ResponseEntity.status(ResponseStatus.OK).body(forecastsService.getForecast(city, dateParsed.atStartOfDay()));
    }
}
