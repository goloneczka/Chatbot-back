package com.pip.chatbot.dao;

import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import com.pip.chatbot.model.Joke;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;

@Repository
public class JokeDao {

    private DSLContext dslContext;

    @Autowired
    public JokeDao(JooqConfiguration jooqConfiguration) {
        this.dslContext = jooqConfiguration.configuration();
    }

    public List<Joke> getAll(){
        Result<Record> result = dslContext.select().from(JOKE).fetch();
        return result.into(Joke.class);
    }


}
