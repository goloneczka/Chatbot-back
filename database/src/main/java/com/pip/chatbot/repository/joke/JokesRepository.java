package com.pip.chatbot.repository.joke;


import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.model.joke.Mark;
import com.pip.chatbot.model.joke.MarkApi;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;
import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;
import static com.pip.chatbot.jooq.jokes.tables.Mark.MARK;
import static org.jooq.impl.DSL.avg;


public class JokesRepository {

    private DSLContext dsl;
    private static final boolean CONFIRMED = true;

    public JokesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }


    public Optional<Joke> getRandomJoke() {
        var result = dsl
                .selectFrom(JOKE)
                .where(JOKE.IS_CONFIRMED.eq(CONFIRMED))
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(result.into(Joke.class));
    }

    public Optional<Joke> getRandomJokeByCategory(String category) {
        var result = dsl
                .selectFrom(JOKE)
                .where(JOKE.CATEGORY.eq(category).and(JOKE.IS_CONFIRMED.eq(CONFIRMED)))
                .orderBy(DSL.rand())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(result.into(Joke.class));
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
                .set(JOKE.IS_CONFIRMED, !CONFIRMED)
                .set(JOKE.JOKE_, joke.getJoke())
                .returning()
                .fetchOne();

        return Optional.ofNullable(result.into(Joke.class));
    }

    public Optional<Category> createTemporaryCategory(String category) {
        var result = dsl.insertInto(CATEGORY)
                .set(JOKE.CATEGORY, category)
                .set(JOKE.IS_CONFIRMED, !CONFIRMED)
                .returning()
                .fetchOne();

        return Optional.ofNullable(result.into(Category.class));
    }

    public Optional<Mark> createMark(Mark mark) {

        var result = dsl.insertInto(MARK)
                .set(MARK.JOKE_ID, mark.getJokeId())
                .set(MARK.MARK_, mark.getMark())
                .returning()
                .fetchOne();

        return Optional.ofNullable(result.into(Mark.class));
    }

    public Optional<MarkApi> getAvgJoke(String id) {
        var result = dsl.select(MARK.JOKE_ID, avg(MARK.MARK_).as("mark"))
                .from(MARK)
                .where(MARK.JOKE_ID.eq(Integer.parseInt(id)))
                .groupBy(MARK.JOKE_ID)
                .fetchOne();

        System.out.println(result.toString());
        return Optional.ofNullable(result.into(MarkApi.class));
    }
}
