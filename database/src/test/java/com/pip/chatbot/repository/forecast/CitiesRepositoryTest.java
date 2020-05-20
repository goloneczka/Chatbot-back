package com.pip.chatbot.repository.forecast;

import com.pip.chatbot.model.forecast.City;
import org.junit.AfterClass;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class CitiesRepositoryTest {

    private final CitiesRepository citiesRepository;

    @Autowired
    public CitiesRepositoryTest(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    @BeforeEach
    public void initRepository() {
        citiesRepository.createCity(new City("City", 100.100F, 200.200F, "Country"));
        citiesRepository.createCity(new City("City2", 300.300F, 400.400F, "Country2"));
    }

    @AfterEach
    public void clearAfterAll(){
        citiesRepository.deleteCity("City1");
        citiesRepository.deleteCity("City2");
        citiesRepository.deleteCity("City3");
        citiesRepository.deleteCity("City4");
    }

    @Test
    public void getCityTest() {
        //when
        City sut = citiesRepository.getCity("City").get();

        //then
        assertEquals("City", sut.getCity());
        assertEquals(100.100F, sut.getLatitude());
        assertEquals(200.200F, sut.getLongitude());
        assertEquals("Country", sut.getCountry());
    }

    @Test
    public void doesCityExistTest(){
        //Given
        String city = "City";

        //when
        Boolean sut = citiesRepository.doesCityExist(city);

        //then
        assertEquals(true, sut);
    }

    @Test
    public void getAllCitiesTest(){
        //when
        List<City> cityList = citiesRepository.getAllCities();

        //then
        assertEquals(2, cityList.size());
    }

    @Test
    public void createCityTest(){
        //Given
        City city = new City("City3", 500.500F, 600.600F, "Country3");

        //when
        City createdCity = citiesRepository.createCity(city);

        //then
        assertEquals("City3", createdCity.getCity());
        assertEquals(500.500F, createdCity.getLatitude());
        assertEquals(600.600F, createdCity.getLongitude());
        assertEquals("Country3", createdCity.getCountry());
    }

    @Test
    public void deleteCityTest(){
        //Given
        String city = "City3";

        //when
        Boolean result = citiesRepository.deleteCity(city);

        //then
        assertEquals(true, result);

    }

    @Test
    public void getCitiesForCountryTest(){
        //Given
        String country = "Country1";

        //when
        List<City> citiesList = citiesRepository.getCitiesForCountry(country);

        //then
        assertEquals(1, citiesList.size());
    }

    @Test
    public void getCitiesWithForecastTest(){
        //when
        List<City> citiesList = citiesRepository.getCitiesWithForecast();

        //then
        assertFalse(citiesList.isEmpty());
    }

    @Test
    public void getCitiesForCountryWithForecastTest(){
        //Given
        String country = "Country1";

        //when
        List<City> citiesList = citiesRepository.getCitiesForCountryWithForecast(country);

        //then
        assertFalse(citiesList.isEmpty());
    }






}
