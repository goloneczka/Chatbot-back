package com.pip.chatbot.controller;

import com.pip.chatbot.jooq.food.Food;
import com.pip.chatbot.model.food.City;
import com.pip.chatbot.model.food.Cuisine;
import com.pip.chatbot.model.food.Dish;
import com.pip.chatbot.model.food.Restaurant;
import com.pip.chatbot.service.food.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;

    @GetMapping(value = "/city")
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity
                .ok()
                .body(foodService.getCities());
    }

    @GetMapping(value = "/city/{cityId}")
    public ResponseEntity<List<Cuisine>> getCuisineForCity(@PathVariable Integer cityId) {
        return ResponseEntity
                .ok()
                .body(foodService.getCuisineForCity(cityId));
    }

    @GetMapping(value = "/city/{cityId}/cuisine/{cuisine}")
    public ResponseEntity<Restaurant> getRestaurantForCusine(@PathVariable Integer cityId, @PathVariable String cuisine) {
        return ResponseEntity
                .ok()
                .body(foodService.getRestaurantForCusine(cityId, cuisine));
    }

    @GetMapping(value = "/restaurant/{restaurantId}/dish")
    public ResponseEntity<List<Dish>> getMenuForRestaurant(@PathVariable Integer restaurantId) {
        return ResponseEntity
                .ok()
                .body(foodService.getDishForRestaurant(restaurantId));
    }
}
