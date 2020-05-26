package com.pip.chatbot.repository.joke;


import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.model.joke.Mark;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;
import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;
import static com.pip.chatbot.jooq.jokes.tables.Mark.MARK;
import static org.jooq.impl.DSL.avg;


public class JokesRepository {

    private DSLContext dsl;
    private ModelMapper modelMapper;
    private static final boolean CONFIRMED = true;

    public JokesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }


    public Optional<Joke> getRandomJoke() {
        return dsl
                .selectFrom(JOKE)
                .where(JOKE.IS_CONFIRMED.eq(CONFIRMED))
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOptionalInto(Joke.class);
    }

    public Optional<Joke> getRandomJokeByCategory(String category) {
        return dsl
                .selectFrom(JOKE)
                .where(JOKE.CATEGORY.eq(category).and(JOKE.IS_CONFIRMED.eq(CONFIRMED)))
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOptionalInto(Joke.class);
    }

    public List<Category> getAllCategories() {
        return dsl.selectFrom(CATEGORY)
                .fetch()
                .into(Category.class);
    }

    public List<Category> getAllConfirmedCategories() {
        return dsl.selectFrom(CATEGORY)
                .where(CATEGORY.IS_CONFIRMED.eq(CONFIRMED))
                .fetch()
                .into(Category.class);
    }

    public Optional<Joke> createTemporaryJoke(Joke joke) {
        var result = dsl.insertInto(JOKE)
                .set(JOKE.CATEGORY, joke.getCategory())
                .set(JOKE.IS_CONFIRMED, joke.isConfirmed())
                .set(JOKE.JOKE_, joke.getJoke())
                .returning()
                .fetchOne().into(Joke.class);
        return Optional.ofNullable(result);
    }

    public Optional<Category> createTemporaryCategory(String category) {
        var result = dsl.insertInto(CATEGORY)
                .set(JOKE.CATEGORY, category)
                .set(JOKE.IS_CONFIRMED, CONFIRMED)
                .returning()
                .fetchOne();

        return Optional.ofNullable(result.into(Category.class));
    }

    public Mark createMark(Mark mark) {

        return dsl.insertInto(MARK)
                .set(MARK.JOKE_ID, mark.getJokeId())
                .set(MARK.MARK_, mark.getMark())
                .returning()
                .fetchOne().into(Mark.class);
    }

    public Optional<Mark> getAvgJokeMark(String id) {
        return dsl.select(MARK.JOKE_ID, avg(MARK.MARK_).as("mark"))
                .from(MARK)
                .where(MARK.JOKE_ID.eq(Integer.parseInt(id)))
                .groupBy(MARK.JOKE_ID)
                .fetchOptionalInto(Mark.class);

    }
}
