package com.pip.chatbot.controller;

import com.pip.chatbot.model.forecast.City;
import com.pip.chatbot.model.forecast.Country;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.forecast.CitiesService;
import com.pip.chatbot.service.forecast.CountriesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/admin/forecasts")
public class AdminForecastsController {
    private final CountriesService countriesService;
    private final CitiesService citiesService;

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getCountries() {
        return ResponseEntity.status(ResponseStatus.OK).body(countriesService.getCountries());
    }

    @PostMapping("/countries")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        return ResponseEntity.status(ResponseStatus.OK).body(countriesService.createCountry(country));
    }
    @PutMapping("/countries/{countryId}")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country, @PathVariable String countryId) {
        return ResponseEntity.status(ResponseStatus.OK).body(countriesService.updateCountry(country, countryId));
    }

    @DeleteMapping("/countries/{country}")
    public ResponseEntity<HashMap.SimpleEntry<String,Boolean>> deleteCountry(@PathVariable String country) {
        countriesService.deleteCountry(country);
        return Response.SUCCESS;
    }

    @GetMapping("/countries/{countryId}/cities")
    public ResponseEntity<List<City>> getCities(@PathVariable String countryId) {
        return ResponseEntity.status(ResponseStatus.OK).body(citiesService.getCities(countryId));
    }

    @PostMapping("/cities")
    public ResponseEntity<City> createCity(@RequestBody City city) {
        return ResponseEntity.status(ResponseStatus.OK).body(citiesService.createCity(city));
    }

    @PutMapping("/cities/{cityId}")
    public ResponseEntity<City> updateCity(@RequestBody City city, @PathVariable String cityId) {
        return ResponseEntity.status(ResponseStatus.OK).body(citiesService.updateCity(city, cityId));
    }

    @DeleteMapping("/cities/{cityId}")
    public ResponseEntity<HashMap.SimpleEntry<String,Boolean>> deleteCity(@PathVariable String cityId) {
        citiesService.deleteCity(cityId);
        return Response.SUCCESS;
    }
}
