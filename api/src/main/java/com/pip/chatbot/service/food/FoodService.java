package com.pip.chatbot.service.food;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.FoodErrorMessages;
import com.pip.chatbot.model.food.City;
import com.pip.chatbot.model.food.Cuisine;
import com.pip.chatbot.model.food.Dish;
import com.pip.chatbot.model.food.Restaurant;
import com.pip.chatbot.repository.food.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public final List<City> getCities() {
        return foodRepository.getCities();
    }

    public List<Cuisine> getCuisineForCity(Integer cityId) {
        return foodRepository.getCuisineForCity(cityId);
    }

    public Restaurant getRandomRestaurantForCuisine(Integer cityId, String cuisine) {
        return foodRepository.getRandomRestaurantForCuisine(cityId, cuisine).orElseThrow(() -> new ChatbotExceptionBuilder().addError(FoodErrorMessages.RESTAURANT_NOT_FOUND).build());
    }

    public List<Dish> getMenuForRestaurant(Integer restaurantId) {
        return foodRepository.getDishForRestaurant(restaurantId);
    }


}
