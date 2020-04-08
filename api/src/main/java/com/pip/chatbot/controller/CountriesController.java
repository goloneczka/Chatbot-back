package com.pip.chatbot.controller;

import com.pip.chatbot.model.Country;
import com.pip.chatbot.repository.CountriesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class CountriesController {
    private final CountriesRepository countriesRepository;

    CountriesController(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    @GetMapping("/admin/countries")
    public ResponseEntity<?> getCountriesList() {
        try {
            return ResponseEntity.ok(countriesRepository.getCountriesList());
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new HashMap.SimpleEntry<>("message", "error"));
        }
    }

    @PostMapping("/admin/countries")
    public ResponseEntity<?> postCountry(@RequestBody Country country) {
        try {
            return ResponseEntity.ok(countriesRepository.createCountry(country));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new HashMap.SimpleEntry<>("message", e.getMessage()));
        }
    }

    @DeleteMapping("/admin/countries/{country}")
    public ResponseEntity<?> deleteCountry(@PathVariable String country) {
        try {
            countriesRepository.deleteCountry(country);
            return ResponseEntity.ok().body("Succesfully deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new HashMap.SimpleEntry<>("message", "country not found"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new HashMap.SimpleEntry<>("message", e.getMessage()));
        }
    }
}
