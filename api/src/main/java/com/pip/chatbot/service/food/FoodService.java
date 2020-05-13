package com.pip.chatbot.service.food;

import com.pip.chatbot.exception.ChatbotException;
import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.FoodErrorMessages;
import com.pip.chatbot.exception.messages.JokesErrorMessages;
import com.pip.chatbot.exception.messages.MarksErrorMessages;
import com.pip.chatbot.model.food.*;
import com.pip.chatbot.repository.food.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return foodRepository.getRandomRestaurantForCuisine(cityId, cuisine)
                .map(restaurant -> {
                    try {
                        Double average = foodRepository.getAvgRestaurantMark(restaurant.getId()).get();
                        restaurant.setAverageUsersRating((average + restaurant.getAverageUsersRating()) / 2);
                    } catch (NullPointerException e) {
                        e.getStackTrace();
                    }
                return restaurant;
                })
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(FoodErrorMessages.RESTAURANT_NOT_FOUND).build());
    }

    public MarkApi rateRestaurant(Mark mark) {
        return foodRepository.createMark(mark).orElseThrow(() -> new ChatbotExceptionBuilder().addError(FoodErrorMessages.RESTAURANT_NOT_FOUND).build());
    }

}
