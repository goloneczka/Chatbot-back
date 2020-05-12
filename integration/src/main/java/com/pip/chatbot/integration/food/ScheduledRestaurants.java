package com.pip.chatbot.integration.food;

import com.pip.chatbot.model.food.City;
import com.pip.chatbot.repository.food.FoodCitiesRepository;
import com.pip.chatbot.repository.food.RestaurantRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@EnableScheduling
@Component
public class ScheduledRestaurants {
    private final FoodCitiesRepository foodCitiesRepository;
    private final RestaurantRepository restaurantRepository;
    private final ZomatoApi zomatoApi;


    public ScheduledRestaurants(FoodCitiesRepository foodCitiesRepository, RestaurantRepository restaurantRepository, ZomatoApi zomatoApi) {
        this.foodCitiesRepository = foodCitiesRepository;
        this.restaurantRepository = restaurantRepository;
        this.zomatoApi = zomatoApi;
    }

    @Scheduled(fixedDelayString = "${application.zomato.restaurantDelay}")
    public void saveRestaurantsData() throws Exception {
        for (City city : foodCitiesRepository.getAllCities()) {
            restaurantRepository.createRestaurants(zomatoApi.getRestaurantsData(city));
        }
    }


}
