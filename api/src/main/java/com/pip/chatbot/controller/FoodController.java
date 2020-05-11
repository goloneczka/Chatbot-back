package com.pip.chatbot.controller;

import com.pip.chatbot.jooq.food.Food;
import com.pip.chatbot.model.food.*;
import com.pip.chatbot.service.food.FoodService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;
    private final ModelMapper modelMapper;

    @GetMapping(value = "/city")
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity
                .ok()
                .body(foodService.getCities());
    }

    @GetMapping(value = "/city/{cityId}/cuisine")
    public ResponseEntity<List<Cuisine>> getCuisineForCity(@PathVariable Integer cityId) {
        return ResponseEntity
                .ok()
                .body(foodService.getCuisineForCity(cityId));
    }

    @GetMapping(value = "/city/{cityId}/cuisine/{cuisine}/restaurant")
    public ResponseEntity<Restaurant> getRestaurantForCuisine(@PathVariable Integer cityId, @PathVariable String cuisine) {
        return ResponseEntity
                .ok()
                .body(foodService.getRandomRestaurantForCuisine(cityId, cuisine));
    }

    @PostMapping("/rate")
    public ResponseEntity<Mark> rateRestaurant(@RequestBody MarkApi markApi) {
        Mark mark = modelMapper.map(markApi, Mark.class);
        return ResponseEntity
                .ok()
                .body(foodService.rateRestaurant(mark));
    }
}
