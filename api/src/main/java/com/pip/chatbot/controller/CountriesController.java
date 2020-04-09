package com.pip.chatbot.controller;

import com.pip.chatbot.model.Country;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CountriesController {
    private final CountryService countryService;

    CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public ResponseEntity<?> getCountries() {
        return ResponseEntity.status(ResponseStatus.OK).body(countryService.getCountries());
    }

    @PostMapping("/admin/countries")
    public ResponseEntity<?> createCountry(@RequestBody Country country) {
        return ResponseEntity.status(ResponseStatus.OK).body(countryService.createCountry(country));
    }

    @DeleteMapping("/admin/countries/{country}")
    public ResponseEntity<?> deleteCountry(@PathVariable String country) {
        countryService.deleteCountry(country);
        return Response.SUCCESS;
    }
}
