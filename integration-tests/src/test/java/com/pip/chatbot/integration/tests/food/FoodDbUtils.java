package com.pip.chatbot.integration.tests.food;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.model.food.*;
import com.pip.chatbot.repository.food.*;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import static com.pip.chatbot.jooq.food.tables.Restaurant.RESTAURANT;
import static com.pip.chatbot.jooq.food.tables.Country.COUNTRY;
import static com.pip.chatbot.jooq.food.tables.City.CITY;
import static com.pip.chatbot.jooq.food.tables.Cuisine.CUISINE;
import static com.pip.chatbot.jooq.food.tables.MarkRestaurant.MARK_RESTAURANT;

public class FoodDbUtils {
    private final DSLContext dsl;
    private final FoodCitiesRepository foodCitiesRepository;
    private final FoodCountriesRepository foodCountriesRepository;
    private final CuisinesRepository cuisinesRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    private final Country country;
    private final City city;
    private final Cuisine cuisine;
    private final Restaurant restaurant;

    public FoodDbUtils(Map<String, String> config) throws SQLException, IOException {
        dsl = DSL.using(DriverManager.getConnection(config.get("url"), config.get("username"), config.get("password")));

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        this.city = objectMapper.readValue(getClass().getResourceAsStream("/food/city.json"), City.class);
        this.cuisine = objectMapper.readValue(getClass().getResourceAsStream("/food/cuisine.json"), Cuisine.class);
        this.country = objectMapper.readValue(getClass().getResourceAsStream("/food/country.json"), Country.class);
        this.restaurant = objectMapper.readValue(getClass().getResourceAsStream("/food/restaurant.json"), Restaurant.class);
        this.restaurant.setCuisines(Arrays.asList(cuisine));

        foodCountriesRepository = new FoodCountriesRepository(dsl);
        foodCitiesRepository = new FoodCitiesRepository(dsl);
        restaurantRepository = new RestaurantRepository(dsl);
        cuisinesRepository = new CuisinesRepository(dsl);
        foodRepository = new FoodRepository(dsl);
    }

    public City insertCountryAndCity() {
        foodCountriesRepository.createCountry(country);
        return foodCitiesRepository.createCity(city);
    }

    public Cuisine insertCuisine() {
        return cuisinesRepository.createCuisine(cuisine);
    }

    public Restaurant insertRestaurant() {
        return restaurantRepository.createRestaurant(restaurant);
    }

    public void clearDb() {
        dsl.deleteFrom(MARK_RESTAURANT).execute();
        dsl.deleteFrom(CUISINE).execute();
        dsl.deleteFrom(RESTAURANT).execute();
        dsl.deleteFrom(CITY).execute();
        dsl.deleteFrom(COUNTRY).execute();
    }
}
