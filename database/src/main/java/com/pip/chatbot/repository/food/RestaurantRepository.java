package com.pip.chatbot.repository.food;

import com.pip.chatbot.jooq.food.Tables;
import com.pip.chatbot.model.food.Cuisine;
import com.pip.chatbot.model.food.Restaurant;
import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RestaurantRepository {
    private final DSLContext dsl;

    public RestaurantRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<Restaurant> getRestaurant(int id) {
        return dsl.select()
                .from(Tables.RESTAURANT)
                .leftJoin(Tables.RESTAURANT_CUISINE)
                .on(Tables.RESTAURANT.ID.eq(Tables.RESTAURANT_CUISINE.RESTAURANT_ID))
                .where(Tables.RESTAURANT.ID.eq(id))
                .fetchGroups(
                        r -> r.into(Tables.RESTAURANT).into(Restaurant.class),
                        r -> r.into(Tables.RESTAURANT_CUISINE).into(Cuisine.class)
                )
                .entrySet()
                .stream()
                .peek(
                        o -> o.getKey().setCuisines(o.getValue())
                ).findAny()
                .map(Map.Entry::getKey);
    }

    public List<Restaurant> getAllRestaurants() {
        return dsl.select()
                .from(Tables.RESTAURANT)
                .leftJoin(Tables.RESTAURANT_CUISINE)
                .on(Tables.RESTAURANT.ID.eq(Tables.RESTAURANT_CUISINE.RESTAURANT_ID))
                .fetchGroups(
                        r -> r.into(Tables.RESTAURANT).into(Restaurant.class),
                        r -> r.into(Tables.RESTAURANT_CUISINE).into(Cuisine.class)
                ).entrySet()
                .stream()
                .peek(
                        o -> o.getKey().setCuisines(o.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public boolean deleteRestaurant(int id) {
        int numberOfRowAffected = dsl.delete(Tables.RESTAURANT)
                .where(Tables.RESTAURANT.ID.eq(id))
                .execute();
        return numberOfRowAffected >= 1;
    }

    public void createRestaurants(List<Restaurant> restaurants) {
        dsl.transaction(outer -> {
            for (Restaurant restaurant : restaurants) {
                createRestaurant(restaurant);
            }
        });
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        Restaurant result = dsl.insertInto(Tables.RESTAURANT)
                .columns(Tables.RESTAURANT.ID, Tables.RESTAURANT.ADDRESS, Tables.RESTAURANT.AVERAGE_USERS_RATING, Tables.RESTAURANT.CITY_ID, Tables.RESTAURANT.NAME, Tables.RESTAURANT.PHONE_NUMBERS)
                .values(restaurant.getId(), restaurant.getAddress(), restaurant.getAverageUsersRating().floatValue(), restaurant.getCityId(), restaurant.getName(), restaurant.getPhoneNumbers())
                .onDuplicateKeyUpdate()
                .set(Tables.RESTAURANT.CITY_ID, restaurant.getCityId())
                .set(Tables.RESTAURANT.ADDRESS, restaurant.getAddress())
                .set(Tables.RESTAURANT.AVERAGE_USERS_RATING, restaurant.getAverageUsersRating().floatValue())
                .set(Tables.RESTAURANT.NAME, restaurant.getName())
                .set(Tables.RESTAURANT.PHONE_NUMBERS, restaurant.getPhoneNumbers())
                .returning()
                .fetchOne()
                .into(Restaurant.class);

        dsl.delete(Tables.RESTAURANT_CUISINE)
                .where(Tables.RESTAURANT_CUISINE.RESTAURANT_ID.eq(restaurant.getId()))
                .execute();

        List<Cuisine> cuisines = new ArrayList<>();

        for (Cuisine cuisine : restaurant.getCuisines()) {
            cuisines.add(dsl.insertInto(Tables.CUISINE)
                    .columns(Tables.CUISINE.CUISINE_)
                    .values(cuisine.getCuisine())
                    .onDuplicateKeyUpdate()
                    .set(Tables.CUISINE.CUISINE_, cuisine.getCuisine())
                    .returning()
                    .fetchOne()
                    .into(Cuisine.class));

            dsl.insertInto(Tables.RESTAURANT_CUISINE)
                    .set(Tables.RESTAURANT_CUISINE.RESTAURANT_ID, restaurant.getId())
                    .set(Tables.RESTAURANT_CUISINE.CUISINE, cuisine.getCuisine())
                    .execute();
        }

        result.setCuisines(cuisines);

        return result;
    }
}
