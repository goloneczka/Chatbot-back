package com.pip.chatbot.repository.joke;

import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import com.pip.chatbot.model.joke.Joke;
import lombok.AllArgsConstructor;
import org.jooq.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;
import static com.pip.chatbot.jooq.jokes.tables.Mark.MARK;

@AllArgsConstructor
public class AdminJokesRepository {
    private final DSLContext dslContext;
    private final ModelMapper modelMapper;

    public List<Joke> getAll() {
        return dslContext
                .selectFrom(JOKE)
                .fetchInto(Joke.class);
    }

    public Optional<Joke> get(int id) {
        return dslContext
                .fetchOptional(JOKE, JOKE.ID.eq(id))
                .map(r -> r.into(Joke.class));
    }

    public Joke create(Joke joke) {
        JokeRecord record = dslContext
                .insertInto(JOKE, JOKE.CATEGORY, JOKE.JOKE_)
                .values(joke.getCategory(), joke.getJoke())
                .returning()
                .fetchOne();

        return modelMapper.map(record, Joke.class);
    }

    public Joke update(Joke joke) {
        JokeRecord record = dslContext
                .update(JOKE)
                .set(JOKE.JOKE_, joke.getJoke())
                .set(JOKE.CATEGORY, joke.getCategory())
                .where(JOKE.ID.eq(joke.getId()))
                .returning()
                .fetchOne();

        return modelMapper.map(record, Joke.class);
    }

    public boolean delete(int id) {
        return 0 < dslContext
                .deleteFrom(JOKE)
                .where(JOKE.ID.eq(id))
                .execute();
    }

    public List<Joke> getAllUnconfirmedJokes() {
        return dslContext
                .selectFrom(JOKE)
                .where(JOKE.IS_CONFIRMED.eq(false))
                .fetchInto(Joke.class);
    }

    public Joke confirmJoke(int id) {
        return dslContext.update(JOKE)
                .set(JOKE.IS_CONFIRMED, true)
                .where(JOKE.ID.eq(id))
                .returning()
                .fetchOne()
                .into(Joke.class);
    }

    public boolean deleteAllJokes() {
        return 0 < dslContext
                .deleteFrom(JOKE)
                .execute();
    }

    public boolean deleteAllMarks() {
        return 0 < dslContext
                .deleteFrom(MARK)
                .execute();
    }
}
