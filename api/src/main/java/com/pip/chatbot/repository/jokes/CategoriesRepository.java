package com.pip.chatbot.repository.jokes;

import com.pip.chatbot.jooq.jokes.tables.records.CategoryRecord;
import com.pip.chatbot.model.jokes.Category;
import com.pip.chatbot.model.jokes.Joke;
import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import lombok.AllArgsConstructor;
import org.jooq.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;
import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;

@Repository
@AllArgsConstructor
public class CategoriesRepository {
    @Autowired
    private final DSLContext dslContext;

    @Autowired
    private final ModelMapper modelMapper;

    public List<Category> getAll() {
        List<CategoryRecord> result = dslContext
                .selectFrom(CATEGORY)
                .fetchInto(CategoryRecord.class);

        List<Category> categories = new ArrayList<>();
        result.forEach(v -> categories.add(modelMapper.map(v, Category.class)));
        return categories;
    }

    public List<Joke> getJokesForCategory(String category) {
        List<JokeRecord> result = dslContext
                .selectFrom(JOKE)
                .where(JOKE.CATEGORY.eq(category))
                .fetchInto(JokeRecord.class);

        List<Joke> jokes = new ArrayList<>();
        result.forEach(v -> jokes.add(modelMapper.map(v, Joke.class)));
        return jokes;
    }

    public Optional<Category> create(Category category) {
        Optional<CategoryRecord> record = dslContext
                .insertInto(CATEGORY)
                .values(category.getCategory())
                .returning(CATEGORY.CATEGORY_)
                .fetchOptional();

        return Optional.ofNullable(record.get().into(Category.class));
    }

    public boolean delete(String category) {
        return 0 < dslContext
                .deleteFrom(CATEGORY)
                .where(CATEGORY.CATEGORY_.eq(category))
                .execute();
    }
}
