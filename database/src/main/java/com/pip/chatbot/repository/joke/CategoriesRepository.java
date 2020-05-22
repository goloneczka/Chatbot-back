package com.pip.chatbot.repository.joke;

import com.pip.chatbot.jooq.jokes.tables.records.CategoryRecord;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import lombok.AllArgsConstructor;
import org.jooq.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;
import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;

@AllArgsConstructor
public class CategoriesRepository {
    private final DSLContext dslContext;
    private final ModelMapper modelMapper;

    public List<Category> getAll() {
        return dslContext
                .selectFrom(CATEGORY)
                .fetchInto(Category.class);
    }

    public List<Joke> getJokesForCategory(String category) {
        return dslContext
                .selectFrom(JOKE)
                .where(JOKE.CATEGORY.eq(category))
                .fetchInto(Joke.class);
    }

    public Category create(Category category) {
        CategoryRecord record = dslContext
                .insertInto(CATEGORY, CATEGORY.CATEGORY_)
                .values(category.getCategory())
                .returning()
                .fetchOne();

        return modelMapper.map(record, Category.class);
    }

    public Optional<Category> update(String category, String value) {
        return dslContext
                .update(CATEGORY)
                .set(CATEGORY.CATEGORY_, value)
                .where(CATEGORY.CATEGORY_.eq(category))
                .returning()
                .fetchOptional()
                .map(record -> modelMapper.map(record, Category.class));
    }

    public boolean delete(String category) {
        return 0 < dslContext
                .deleteFrom(CATEGORY)
                .where(CATEGORY.CATEGORY_.eq(category))
                .execute();
    }
}
