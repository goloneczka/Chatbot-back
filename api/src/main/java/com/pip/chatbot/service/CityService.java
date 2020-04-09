package com.pip.chatbot.service;

import com.pip.chatbot.Exception.ChatbotExceptionBuilder;
import com.pip.chatbot.Exception.messages.CitiesErrorMessages;
import com.pip.chatbot.Exception.messages.CountriesErrorMessages;
import com.pip.chatbot.model.City;
import com.pip.chatbot.repository.CitiesRepository;
import com.pip.chatbot.repository.CountriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CitiesRepository citiesRepository;
    private final CountriesRepository countriesRepository;

    public CityService(CitiesRepository citiesRepository, CountriesRepository countriesRepository) {
        this.citiesRepository = citiesRepository;
        this.countriesRepository = countriesRepository;
    }

    public City getCity(String cityName) {
        Optional<City> city = citiesRepository.getCity(cityName);
        if (city.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(CitiesErrorMessages.NOT_FOUND).build();
        }
        return city.get();
    }

    public List<City> getCities() {
        return citiesRepository.getAllCities();
    }

    public List<City> getCities(String country) {
        if (!countriesRepository.isCountryExist(country)) {
            throw new ChatbotExceptionBuilder().addError(CountriesErrorMessages.NOT_FOUND).build();
        }

        List<City> cities = citiesRepository.getCitiesForCountry(country);

        if (cities.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(CitiesErrorMessages.NOT_FOUND).build();
        }
        return cities;
    }

    public City createCity(City city) {
        Optional<City> optionalCity = citiesRepository.createCity(city);
        if (optionalCity.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(CitiesErrorMessages.CREATE_FAILURE).build();
        }
        return optionalCity.get();
    }

    public void deleteCity(String city) {
        if (!citiesRepository.deleteCity(city)) {
            throw new ChatbotExceptionBuilder().addError(CitiesErrorMessages.DELETE_FAILURE).build();
        }
    }
}
