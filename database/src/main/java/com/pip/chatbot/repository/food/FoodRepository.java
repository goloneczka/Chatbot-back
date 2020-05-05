package com.pip.chatbot.repository.food;

import com.pip.chatbot.model.food.City;
import com.pip.chatbot.model.food.Cuisine;
import com.pip.chatbot.model.food.Dish;
import com.pip.chatbot.model.food.Restaurant;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.food.Food.FOOD;

@AllArgsConstructor
public class FoodRepository {
    private final DSLContext dsl;

    public List<City> getCities() {
        return dsl
                .select()
                .from(FOOD.CITY)
                .fetchInto(City.class);
    }

    public List<Cuisine> getCuisineForCity(Integer cityId) {
        return dsl
                .select(FOOD.RESTAURANT_CUISINE.CUISINE)
                .from(FOOD.CITY)
                .join(FOOD.RESTAURANT)
                .on(FOOD.RESTAURANT.ID.eq(FOOD.CITY.ID))
                .join(FOOD.RESTAURANT_CUISINE)
                .on(FOOD.RESTAURANT_CUISINE.RESTAURANT_ID.eq(FOOD.RESTAURANT.ID))
                .where(FOOD.CITY.ID.eq(cityId))
                .fetchInto(Cuisine.class);

    }

    public Optional<Restaurant> getRandomRestaurantForCuisine(Integer cityId, String cuisine) {
        return Optional.ofNullable(dsl
                .select()
                .from(FOOD.RESTAURANT)
                .join(FOOD.RESTAURANT_CUISINE)
                .on(FOOD.RESTAURANT_CUISINE.RESTAURANT_ID.eq(FOOD.RESTAURANT.ID))
                .where(FOOD.RESTAURANT.CITY_ID.eq(cityId), FOOD.RESTAURANT_CUISINE.CUISINE.eq(cuisine))
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOne()
                .into(Restaurant.class));
    }

    public List<Dish> getDishForRestaurant(Integer restaurantId) {
        return dsl
                .select(FOOD.DISH.ID, FOOD.DISH.DISH_, FOOD.DISH.PRICE)
                .from(FOOD.MENU)
                .join(FOOD.MENU_DISH)
                .on(FOOD.MENU.ID.eq(FOOD.MENU_DISH.MENU_ID))
                .join(FOOD.DISH)
                .on(FOOD.MENU_DISH.DISH_ID.eq(FOOD.DISH.ID))
                .where(FOOD.MENU.RESTAURANT_ID.eq(restaurantId))
                .fetchInto(Dish.class);
    }


}
