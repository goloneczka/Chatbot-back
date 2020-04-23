package com.pip.chatbot.repository;

import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import com.pip.chatbot.model.Joke;
import lombok.AllArgsConstructor;
import org.jooq.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;

@AllArgsConstructor
public class AdminJokesRepository {
    private final DSLContext dslContext;
    private final ModelMapper modelMapper;

    public List<Joke> getAll() {
        List<JokeRecord> result = dslContext
                .selectFrom(JOKE)
                .fetchInto(JokeRecord.class);

        List<Joke> jokes = new ArrayList<>();
        result.forEach(v -> jokes.add(modelMapper.map(v, Joke.class)));
        return jokes;
    }

    public Optional<Joke> get(int id) {
        Optional<JokeRecord> record = dslContext
                .fetchOptional(JOKE, JOKE.ID.eq(id));

        return Optional.ofNullable(record.get().into(Joke.class));
    }

    public Optional<Joke> create(Joke joke) {
        Optional<JokeRecord> record = dslContext
                .insertInto(JOKE, JOKE.CATEGORY, JOKE.JOKE_)
                .values(joke.getCategory(), joke.getJoke())
                .returning(JOKE.ID, JOKE.CATEGORY, JOKE.JOKE_)
                .fetchOptional();

        return Optional.ofNullable(record.get().into(Joke.class));
    }

    public Optional<Joke> update(Joke joke) {
        Optional<JokeRecord> record = dslContext
                .update(JOKE)
                .set(JOKE.CATEGORY, joke.getCategory())
                .set(JOKE.JOKE_, joke.getJoke())
                .where(JOKE.ID.eq(joke.getId()))
                .returning(JOKE.ID, JOKE.CATEGORY, JOKE.JOKE_)
                .fetchOptional();

        return Optional.ofNullable(record.get().into(Joke.class));
    }

    public boolean delete(int id) {
        return 0 < dslContext
                .deleteFrom(JOKE)
                .where(JOKE.ID.eq(id))
                .execute();
    }
}
