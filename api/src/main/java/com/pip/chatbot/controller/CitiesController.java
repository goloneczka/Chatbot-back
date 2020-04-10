package com.pip.chatbot.controller;

import com.pip.chatbot.model.City;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.CitiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            cities = citiesService.getCities(country.get());
        else
            cities = citiesService.getCities();
        return ResponseEntity.status(ResponseStatus.OK).body(cities);
    }

    @GetMapping("/cities/{city}")
    public ResponseEntity<?> getCity(@PathVariable String city) {
        return ResponseEntity.status(ResponseStatus.OK).body(citiesService.getCity(city));
    }

    @PostMapping("/admin/cities")
    public ResponseEntity<?> createCity(@RequestBody City city) {
        return ResponseEntity.status(ResponseStatus.OK).body(citiesService.createCity(city));
    }

    @DeleteMapping("/admin/cities/{city}")
    public ResponseEntity<?> deleteCity(@PathVariable String city) {
        citiesService.deleteCity(city);
        return Response.SUCCESS;
    }
}
