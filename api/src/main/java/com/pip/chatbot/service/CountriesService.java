package com.pip.chatbot.service;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.CountriesErrorMessages;
import com.pip.chatbot.model.Country;
import com.pip.chatbot.repository.CountriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountriesService {

    private final CountriesRepository countriesRepository;
    
    public CountriesService(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    public List<Country> getCountries() {
        return countriesRepository.getCountriesList();
    }

    public Country createCountry(Country country) {
        return countriesRepository.createCountry(country);
    }

    public void deleteCountry(String country) {
        if (!countriesRepository.deleteCountry(country)) {
            throw new ChatbotExceptionBuilder().addError(CountriesErrorMessages.NOT_FOUND).build();
        }
    }

}
