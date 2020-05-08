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
        var result = dsl
                .select()
                .from(FOOD.RESTAURANT)
                .join(FOOD.RESTAURANT_CUISINE)
                .on(FOOD.RESTAURANT_CUISINE.RESTAURANT_ID.eq(FOOD.RESTAURANT.ID))
                .where(FOOD.RESTAURANT.CITY_ID.eq(cityId), FOOD.RESTAURANT_CUISINE.CUISINE.eq(cuisine))
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOne()
                .into(Restaurant.class);

        result.setAverageUsersRating(getAvgRestaurantMark(result.getId(), result.getAverageUsersRating()).get());

        return Optional.of(result);
    }

    public Optional<Mark> createMark(Mark mark) {
        var result = dsl.insertInto(MARK_RESTAURANT)
                .set(MARK_RESTAURANT.RESTAURANT_ID, mark.getRestaurantId())
                .set(MARK_RESTAURANT.MARK, mark.getMark())
                .returning()
                .fetchOne();

        return Optional.ofNullable(result.into(Mark.class));
    }

    public Optional<Double> getAvgRestaurantMark(int id, Double zomatoMark) {
        var avgMark = dsl.select(avg(MARK_RESTAURANT.MARK).as("mark"))
                .from(MARK_RESTAURANT)
                .where(MARK_RESTAURANT.RESTAURANT_ID.eq(id))
                .groupBy(MARK_RESTAURANT.RESTAURANT_ID)
                .fetchOne();

        return Optional.of(avgMark.value1().doubleValue() + zomatoMark / 2);
    }

}
