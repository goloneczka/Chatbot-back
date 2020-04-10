package com.pip.chatbot.controller;

import com.pip.chatbot.model.Country;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.CountriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CountriesController {
    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/countries")
    public ResponseEntity<?> getCountries() {
        return ResponseEntity.status(ResponseStatus.OK).body(countriesService.getCountries());
    }

    @PostMapping("/admin/countries")
    public ResponseEntity<?> createCountry(@RequestBody Country country) {
        return ResponseEntity.status(ResponseStatus.OK).body(countriesService.createCountry(country));
    }

    @DeleteMapping("/admin/countries/{country}")
    public ResponseEntity<?> deleteCountry(@PathVariable String country) {
        countriesService.deleteCountry(country);
        return Response.SUCCESS;
    }
}
