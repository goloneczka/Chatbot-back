package com.pip.chatbot.repository.joke;

import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import com.pip.chatbot.model.joke.Joke;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
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
        return dslContext.selectFrom(JOKE)
                .where(JOKE.ID.eq(id))
                .fetchOptionalInto(Joke.class);
    }

    public Joke create(Joke joke) {
        return dslContext
                .insertInto(JOKE, JOKE.CATEGORY, JOKE.JOKE_)
                .values(joke.getCategory(), joke.getJoke())
                .returning()
                .fetchOne()
                .into(Joke.class);
    }

    public Optional<Joke> update(Joke joke) {
        return dslContext
                .update(JOKE)
                .set(JOKE.JOKE_, joke.getJoke())
                .set(JOKE.CATEGORY, joke.getCategory())
                .where(JOKE.ID.eq(joke.getId()))
                .returning()
                .fetchOptional()
                .map(record -> record.into(Joke.class));
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

    public Optional<Joke> confirmJoke(int id) {
        return dslContext.update(JOKE)
                .set(JOKE.IS_CONFIRMED, true)
                .where(JOKE.ID.eq(id))
                .returning()
                .fetchOptional()
                .map(record -> modelMapper.map(record, Joke.class));
    }
}
