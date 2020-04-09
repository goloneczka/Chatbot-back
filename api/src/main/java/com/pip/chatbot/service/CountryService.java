package com.pip.chatbot.service;

import com.pip.chatbot.Exception.ChatbotExceptionBuilder;
import com.pip.chatbot.Exception.messages.CountriesErrorMessages;
import com.pip.chatbot.model.Country;
import com.pip.chatbot.repository.CountriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountriesRepository countriesRepository;

    public CountryService(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    public List<Country> getCountries() {
        return countriesRepository.getCountriesList();
    }

    public Country createCountry(Country country) {
        Optional<Country> optionalCountry = countriesRepository.createCountry(country);
        if (optionalCountry.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(CountriesErrorMessages.CREATE_FAILURE).build();
        }
        return optionalCountry.get();
    }

    public void deleteCountry(String country) {
        if (!countriesRepository.deleteCountry(country)) {
            throw new ChatbotExceptionBuilder().addError(CountriesErrorMessages.DELETE_FAILURE).build();
        }
    }

}
