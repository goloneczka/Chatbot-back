package com.pip.chatbot.repository.food;

import com.pip.chatbot.jooq.food.Tables;
import com.pip.chatbot.model.food.Dish;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;


public class DishesRepository {
    private final DSLContext dsl;

    public DishesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<Dish> getDish(int id) {
        return Optional.ofNullable(dsl.selectFrom(Tables.DISH)
                .where(Tables.DISH.ID.eq(id))
                .fetchAnyInto(Dish.class));
    }

    public List<Dish> getAllDishes() {
        return dsl.selectFrom(Tables.DISH).fetchInto(Dish.class);
    }

    public Dish createDish(Dish dish) {
        return dsl.insertInto(Tables.DISH)
                .columns(Tables.DISH.ID, Tables.DISH.DISH_, Tables.DISH.PRICE)
                .values(dish.getId(), dish.getDish(), dish.getPrice())
                .onDuplicateKeyUpdate()
                .set(Tables.DISH.DISH_, dish.getDish())
                .set(Tables.DISH.PRICE, dish.getPrice())
                .returning()
                .fetchOne()
                .into(Dish.class);
    }

    public boolean deleteDish(int id) {
        int numberOfRowsAffected = dsl.delete(Tables.DISH)
                .where(Tables.DISH.ID.eq(id))
                .execute();
        return numberOfRowsAffected >= 1;
    }
}
