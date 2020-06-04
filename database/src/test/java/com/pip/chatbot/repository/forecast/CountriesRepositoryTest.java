package com.pip.chatbot.repository.forecast;
import com.pip.chatbot.model.forecast.Country;
import com.pip.chatbot.repository.DslContextFactory;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.pip.chatbot.jooq.weather.tables.Country.COUNTRY;

public class CountriesRepositoryTest {

    private DSLContext dslContext;
    private CountriesRepository countriesRepository;

    @BeforeEach
    void init(){
        DslContextFactory dslContextFactory = new DslContextFactory();
        this.dslContext = dslContextFactory.getDslContext();
        this.countriesRepository = new CountriesRepository(dslContext);
    }

    @AfterEach
    void clearDatabase(){
        dslContext.truncateTable(COUNTRY).cascade().execute();
    }

    void addCountryToDatabase(){
        countriesRepository.createCountry(new Country("Country"));
    }

    @Test
    void createCountryReturnsCountry() {
        Country country = new Country("Country");

        Assertions.assertThat(countriesRepository.createCountry(country))
                .isEqualTo(country);

        Assertions.assertThat(countriesRepository.getCountriesList())
                .contains(country);
    }

    @Test
    void updateCountryReturnsCountry() {
        this.addCountryToDatabase();

        Assertions.assertThat(countriesRepository.updateCountry("Country", new Country("CountryUpdated")))
                .get()
                .isEqualTo(new Country("CountryUpdated"));

        Assertions.assertThat(countriesRepository.getCountriesList())
                .contains(new Country("CountryUpdated"));
    }

    @Test
    void updateCountryGivenNonExistingCountryReturnsEmpty() {
        Assertions.assertThat(countriesRepository.updateCountry("Country1", new Country("CountryUpdated")))
                .isEmpty();
    }

    @Test
    void doesCountryExistReturnsTrue() {
        this.addCountryToDatabase();
        Assertions.assertThat(countriesRepository.doesCountryExist("Country"))
                .isTrue();
    }

    @Test
    void doesCountryExistGivenNonExistingCountryReturnsFalse() {
        Assertions.assertThat(countriesRepository.doesCountryExist("Country"))
                .isFalse();
    }

    @Test
    void getCountriesListReturnsCountriesList() {
        this.addCountryToDatabase();

        Assertions.assertThat(countriesRepository.getCountriesList())
                .isNotEmpty()
                .contains(new Country("Country"));
    }

    @Test
    void getCountriesListReturnEmptyList() {
        Assertions.assertThat(countriesRepository.getCountriesList())
            .isEmpty();
    }

    @Test
    void deleteCountryReturnsTrue() {
        this.addCountryToDatabase();

        Assertions.assertThat(countriesRepository.deleteCountry("Country"))
                .isTrue();
    }

    @Test
    void deleteCountryGivenNonExistingCountryReturnsFalse() {
        Assertions.assertThat(countriesRepository.deleteCountry("Country"))
                .isFalse();
    }
}
