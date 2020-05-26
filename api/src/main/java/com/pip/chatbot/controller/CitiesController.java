package com.pip.chatbot.controller;

import com.pip.chatbot.model.forecast.City;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.forecast.CitiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class CitiesController {
    private final CitiesService citiesService;

    public CitiesController(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCities(@RequestParam Optional<String> country) {
        List<City> cities;
        if (country.isPresent())
            cities = citiesService.getCitiesWithForecast(country.get());
        else
            cities = citiesService.getCitiesWithForecast();
        return ResponseEntity.status(ResponseStatus.OK).body(cities);
    }

    @GetMapping("/cities/{city}")
    public ResponseEntity<City> getCity(@PathVariable String city) {
        return ResponseEntity.status(ResponseStatus.OK).body(citiesService.getCity(city));
    }

}
