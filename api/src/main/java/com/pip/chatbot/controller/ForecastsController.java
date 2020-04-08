package com.pip.chatbot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.repository.ForecastRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
public class ForecastsController {
    private final ForecastRepository forecastRepository;
    private final ObjectMapper objectMapper;

    public ForecastsController(ForecastRepository forecastRepository, ObjectMapper objectMapper) {
        this.forecastRepository = forecastRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("admin/forecasts/city/{city}")
    public ResponseEntity<?> getForecastsForCity(@PathVariable String city) {
        try {
            return ResponseEntity.ok().body(forecastRepository.getForecastsForCity(city));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new HashMap.SimpleEntry<>("message", "City not found"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new HashMap.SimpleEntry<>("message", e.getMessage()));
        }
    }

    @GetMapping("admin/forecasts/cityAndDate/{city}")
    public ResponseEntity<?> getForecastForDayAndCity(@PathVariable String city, @RequestBody JsonNode dateJson) {
        try {
            LocalDate date = LocalDate.parse(dateJson.get("date").asText());
            return ResponseEntity.ok().body(forecastRepository.getForecastsForCityAndDate(city, date.atStartOfDay()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new HashMap.SimpleEntry<>("message", "City not found"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new HashMap.SimpleEntry<>("message", e.getMessage()));
        }
    }
}
