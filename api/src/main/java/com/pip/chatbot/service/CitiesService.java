package com.pip.chatbot.service;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.CitiesErrorMessages;
import com.pip.chatbot.exception.messages.CountriesErrorMessages;
import com.pip.chatbot.model.City;
import com.pip.chatbot.repository.CitiesRepository;
import com.pip.chatbot.repository.CountriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesService {
    private final CitiesRepository citiesRepository;

    public CitiesService(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    public City getCity(String cityName) {
        return citiesRepository.getCity(cityName)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(CitiesErrorMessages.NOT_FOUND).build());
    }

    public List<City> getCities() {
        return citiesRepository.getAllCities();
    }

    public List<City> getCities(String country) {

        return citiesRepository.getCitiesForCountry(country);
    }

    public City createCity(City city) {
        return citiesRepository.createCity(city);
    }

    public void deleteCity(String city) {
        if (!citiesRepository.deleteCity(city)) {
            throw new ChatbotExceptionBuilder().addError(CitiesErrorMessages.NOT_FOUND).build();
        }
    }
}
