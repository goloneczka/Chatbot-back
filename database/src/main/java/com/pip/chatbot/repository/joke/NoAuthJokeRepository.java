package com.pip.chatbot.repository.joke;


import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;
import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;


public class NoAuthJokeRepository {

    private DSLContext dsl;

    public NoAuthJokeRepository(DSLContext dsl) {
        this.dsl = dsl;
    }


    public Optional<Joke> getById(Integer value) {
        JokeRecord result = dsl
                .selectFrom(JOKE)
                .where(JOKE.ID.eq(value))
                .fetchOne();

        return Optional.ofNullable(result.into(Joke.class));
    }

    public int getJokesTableSize() {
        return dsl
                .selectCount()
                .from(JOKE)
                .fetchOne(0, int.class);
    }


    public Optional<Joke> getRandomOneByCategory(String category) {
        JokeRecord result = dsl
                .selectFrom(JOKE)
                .where(JOKE.CATEGORY.eq(category))
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(result.into(Joke.class));
    }

    public Optional<List<Category>> getAllCategory() {
        Result<Record> result = dsl.select().from(CATEGORY).fetch();

        return Optional.ofNullable(result.into(Category.class));

    }

    public boolean addJoke(String joke) {
        // TODO
        // need new table
        return true;
    }
}
