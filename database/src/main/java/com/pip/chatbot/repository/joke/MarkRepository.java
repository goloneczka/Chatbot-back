package com.pip.chatbot.repository.joke;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.model.forecast.City;
import com.pip.chatbot.model.joke.Mark;
import org.jooq.DSLContext;

import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Mark.Mark;

public class MarkRepository {

    private DSLContext dsl;

    public MarkRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<Mark> createMark(String id, Double mark) {
        dsl.insertInto(MARK)
                .set(Tables.CITY.CITY_, city.getCity())
                .set(Tables.CITY.COUNTRY, city.getCountry())
                .returningResult()
                .fetchOne()
                .into(City.class);
    }
}
