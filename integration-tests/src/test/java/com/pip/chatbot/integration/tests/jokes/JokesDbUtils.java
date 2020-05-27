package com.pip.chatbot.integration.tests.jokes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.model.joke.Mark;
import com.pip.chatbot.model.joke.MarkApi;
import com.pip.chatbot.repository.joke.AdminJokesRepository;
import com.pip.chatbot.repository.joke.CategoriesRepository;
import com.pip.chatbot.repository.joke.JokesRepository;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.sql.DriverManager;
import java.util.Map;

import static com.pip.chatbot.jooq.jokes.tables.Mark.MARK;
import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;
import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;

public class JokesDbUtils {
    private final DSLContext dsl;
    private final AdminJokesRepository adminJokesRepository;
    private final CategoriesRepository categoriesRepository;
    private final JokesRepository jokesRepository;
    private final Category category;
    private final Joke joke;
    private final Mark mark;

    public JokesDbUtils(Map<String, String> config) throws Exception {
        dsl = DSL.using(DriverManager.getConnection(config.get("url"), config.get("username"), config.get("password")));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

        category = objectMapper.readValue(getClass().getResourceAsStream("/jokes/category.json"), Category.class);
        joke = objectMapper.readValue(getClass().getResourceAsStream("/jokes/joke.json"), Joke.class);
        mark = objectMapper.readValue(getClass().getResourceAsStream("/jokes/mark.json"), Mark.class);

        adminJokesRepository = new AdminJokesRepository(dsl, mapper);
        categoriesRepository = new CategoriesRepository(dsl, mapper);
        jokesRepository = new JokesRepository(dsl);
    }

    public Category insertCategory() {
        return categoriesRepository.create(category);
    }

    public Joke insertJoke() {
        return adminJokesRepository.create(joke);
    }

    public Mark insertMark(int jokeId) {
        mark.setJokeId(jokeId);
        return jokesRepository.createMark(mark);
    }

    public void clearDb() {
        dsl.deleteFrom(MARK).execute();
        dsl.deleteFrom(JOKE).execute();
        dsl.deleteFrom(CATEGORY).execute();
    }


}
