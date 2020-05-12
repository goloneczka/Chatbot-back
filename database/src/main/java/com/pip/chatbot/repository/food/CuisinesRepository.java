package com.pip.chatbot.repository.food;

import com.pip.chatbot.jooq.food.Tables;
import com.pip.chatbot.model.food.Cuisine;
import org.jooq.DSLContext;

import java.util.List;

public class CuisinesRepository {
    private final DSLContext dsl;

    public CuisinesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<Cuisine> getAllCuisines() {
        return dsl.selectFrom(Tables.CUISINE).fetchInto(Cuisine.class);
    }

    public Cuisine createCuisine(Cuisine cuisine) {
        return dsl.insertInto(Tables.CUISINE)
                .columns(Tables.CUISINE.CUISINE_)
                .values(cuisine.getCuisine())
                .onDuplicateKeyUpdate()
                .set(Tables.CUISINE.CUISINE_, cuisine.getCuisine())
                .returning()
                .fetchOne()
                .into(Cuisine.class);
    }

    public boolean deleteCuisine(String cuisine) {
        int numberOfRowsAffected = dsl.delete(Tables.CUISINE)
                .where(Tables.CUISINE.CUISINE_.eq(cuisine))
                .execute();
        return numberOfRowsAffected >= 1;
    }
}
