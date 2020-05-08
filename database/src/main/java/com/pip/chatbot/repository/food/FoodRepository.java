package com.pip.chatbot.repository.food;

import com.pip.chatbot.model.food.City;
import com.pip.chatbot.model.food.Cuisine;
import com.pip.chatbot.model.food.Mark;
import com.pip.chatbot.model.food.Restaurant;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.food.Food.FOOD;
import static com.pip.chatbot.jooq.food.tables.MarkRestaurant.MARK_RESTAURANT;
import static org.jooq.impl.DSL.avg;

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

    public Optional<Mark> createMark(Mark mark) {
        var result = dsl.insertInto(MARK_RESTAURANT)
                .set(MARK_RESTAURANT.RESTAURANT_ID, mark.getRestaurantId())
                .set(MARK_RESTAURANT.MARK, mark.getMark())
                .returning()
                .fetchOne();

        return Optional.ofNullable(result.into(Mark.class));
    }

    public Optional<Mark> getAvgRestaurantMark(String id) {
        var avgMark = dsl.select(MARK_RESTAURANT.RESTAURANT_ID, avg(MARK_RESTAURANT.MARK).as("mark"))
                .from(MARK_RESTAURANT)
                .where(MARK_RESTAURANT.RESTAURANT_ID.eq(Integer.parseInt(id)))
                .groupBy(MARK_RESTAURANT.RESTAURANT_ID)
                .fetchOne();

        var result = dsl.select(FOOD.RESTAURANT.ID, (FOOD.RESTAURANT.AVERAGE_USERS_RATING.add(avgMark.field2()).div(2)).as("mark"))
                .from(FOOD.RESTAURANT)
                .where(FOOD.RESTAURANT.ID.eq(Integer.parseInt(id)))
                .fetchOne();

        return Optional.ofNullable(result.into(Mark.class));
    }

}
