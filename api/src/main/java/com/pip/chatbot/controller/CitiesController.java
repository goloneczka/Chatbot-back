package com.pip.chatbot.controller;

import com.pip.chatbot.model.City;
import com.pip.chatbot.repository.CitiesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class CitiesController {
    private final CitiesRepository citiesRepository;

    CitiesController(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    @GetMapping("/admin/cities")
    public ResponseEntity<?> getCitiesList() {
        try {
            return ResponseEntity.ok(citiesRepository.getAllCities());
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new HashMap.SimpleEntry<>("message", "error"));
        }
    }

    @GetMapping("/admin/cities/{city}")
    public ResponseEntity<?> getCity(@PathVariable String city) {
        try {
            return ResponseEntity.ok().body(citiesRepository.getCity(city));
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

    @PostMapping("/admin/cities")
    public ResponseEntity<?> postCity(@RequestBody City city) {
        try {
            System.out.println(city);
            return ResponseEntity.ok(citiesRepository.createCity(city));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new HashMap.SimpleEntry<>("message", e.getMessage()));
        }
    }

    @DeleteMapping("/admin/cities/{city}")
    public ResponseEntity<?> deleteCity(@PathVariable String city) {
        try {
            citiesRepository.deleteCity(city);
            return ResponseEntity.ok().body("Succesfully deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new HashMap.SimpleEntry<>("message", "city not found"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new HashMap.SimpleEntry<>("message", e.getMessage()));
        }
    }

    @GetMapping("/admin/cities/country/{country}")
    public ResponseEntity<?> getCitiesForCountry(@PathVariable String country) {
        try {
            return ResponseEntity.ok(citiesRepository.getCitiesForCountry(country));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new HashMap.SimpleEntry<>("message", "country not found"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new HashMap.SimpleEntry<>("message", "error"));
        }
    }
}
