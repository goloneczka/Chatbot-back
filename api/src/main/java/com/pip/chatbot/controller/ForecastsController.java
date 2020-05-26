package com.pip.chatbot.controller;

import com.pip.chatbot.model.forecast.Forecast;
import com.pip.chatbot.model.forecast.ForecastApi;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.forecast.ForecastsService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class ForecastsController {
    private final ForecastsService forecastsService;
    private final ModelMapper modelMapper;

    public ForecastsController(ForecastsService forecastsService, ModelMapper modelMapper) {
        this.forecastsService = forecastsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("forecasts/city/{city}")
    public ResponseEntity<ForecastApi> getForecastsForCity(@PathVariable String city, @RequestParam String date) {
        LocalDate dateParsed = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return ResponseEntity.status(ResponseStatus.OK).body(modelMapper.map(
                forecastsService.getForecast(city, dateParsed.atStartOfDay()), ForecastApi.class));
    }
}
