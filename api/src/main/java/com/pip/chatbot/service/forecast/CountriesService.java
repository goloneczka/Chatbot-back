package com.pip.chatbot.service.forecast;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.CountriesErrorMessages;
import com.pip.chatbot.model.forecast.Country;
import com.pip.chatbot.repository.forecast.CountriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
