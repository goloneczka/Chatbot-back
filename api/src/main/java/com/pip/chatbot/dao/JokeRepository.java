package com.pip.chatbot.dao;


import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import com.pip.chatbot.model.Category;
import com.pip.chatbot.model.Joke;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;
import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;

@Repository
public class JokeRepository {

    private DSLContext dslContext;

    @Autowired
    public JokeRepository(JooqConfiguration jooqConfiguration) {
        this.dslContext = jooqConfiguration.configuration();
    }

    public Optional<List<Joke>> getAllJoke() {
        Result<Record> result = dslContext.select().from(JOKE).fetch();
        return Optional.ofNullable(result.into(Joke.class));
    }


    public Optional<Joke> getById(Integer value) {
        JokeRecord result = dslContext
                .selectFrom(JOKE)
                .where(JOKE.ID.eq(value))
                .fetchOne();

        return Optional.ofNullable(result.into(Joke.class));
    }

    public int getJokesTableSize() {
        return dslContext
                .selectCount()
                .from(JOKE)
                .fetchOne(0, int.class);
    }


    public Optional<Joke> getRandomOneByCategory(String category) {
        JokeRecord result = dslContext
                .selectFrom(JOKE)
                .where(JOKE.CATEGORY.eq(category))
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(result.into(Joke.class));
    }

    public Optional<List<Category>> getAllCategory() {
        Result<Record> result = dslContext.select().from(CATEGORY).fetch();

        return Optional.ofNullable(result.into(Category.class));

    }

    public boolean addJoke(String joke) {
        // TODO
        // need new table
        return true;
    }
}
