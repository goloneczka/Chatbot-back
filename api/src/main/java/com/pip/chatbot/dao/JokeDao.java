package com.pip.chatbot.dao;


import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import com.pip.chatbot.model.Joke;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;

@Repository
public class JokeDao {

    private DSLContext dslContext;

    @Autowired
    public JokeDao(JooqConfiguration jooqConfiguration) {
        this.dslContext = jooqConfiguration.configuration();
    }

    public Optional<List<Joke>> getAll() {
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

}
