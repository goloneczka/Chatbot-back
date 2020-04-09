package com.pip.chatbot.controller;

import com.pip.chatbot.model.City;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CitiesController {
    private final CityService cityService;

    CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCities(@RequestParam Optional<String> country) {
        List<City> cities;
        if (country.isPresent())
            cities = cityService.getCities(country.get());
        else
            cities = cityService.getCities();
        return ResponseEntity.status(ResponseStatus.OK).body(cities);
    }

    @GetMapping("/cities/{city}")
    public ResponseEntity<?> getCity(@PathVariable String city) {
        return ResponseEntity.status(ResponseStatus.OK).body(cityService.getCity(city));
    }

    @PostMapping("/admin/cities")
    public ResponseEntity<?> createCity(@RequestBody City city) {
        return ResponseEntity.status(ResponseStatus.OK).body(cityService.createCity(city));
    }

    @DeleteMapping("/admin/cities/{city}")
    public ResponseEntity<?> deleteCity(@PathVariable String city) {
        cityService.deleteCity(city);
        return Response.SUCCESS;
    }
}
