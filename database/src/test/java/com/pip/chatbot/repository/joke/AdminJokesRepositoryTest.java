package com.pip.chatbot.repository.joke;

import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.repository.DslContextFactory;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;
import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;

public class AdminJokesRepositoryTest {

    private DSLContext dslContext;
    private AdminJokesRepository repository;
    private JokesRepository jokesRepository;

    @BeforeEach
    public void init() throws ClassNotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        DslContextFactory dslContextFactory = new DslContextFactory();
        dslContext = dslContextFactory.getDslContext();
        CategoriesRepository categoriesRepository = new CategoriesRepository(dslContext, modelMapper);
        jokesRepository = new JokesRepository(dslContext);
        Category category = new Category("Category", true);
        categoriesRepository.create(category);

        repository = new AdminJokesRepository(dslContext, modelMapper);
    }

    @AfterEach
    public void truncateTable() {
        dslContext.truncateTable(JOKE).cascade().execute();
        dslContext.truncateTable(CATEGORY).cascade().execute();
    }


    @Test
    void getAllReturnsListJokes() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(repository.getAll())
                .isNotEmpty()
                .isEqualTo(
                        Collections.singletonList(
                                new Joke(repository.getAll().get(0).getId(), "Joke", "Category", false)
                        )
                );
    }

    @Test
    void getAllWithEmptyDatabaseReturnsEmptyList() {
        Assertions.assertThat(repository.getAll())
                .isEmpty();
    }

    @Test
    void getReturnsJoke() {
        Assertions.assertThat(repository.create(new Joke(1, "Joke", "Category", true)))
                .isEqualTo(repository.getAll().get(0));
    }


    @Test
    void updateReturnsJoke() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(
                repository.update(new Joke(repository.getAll().get(0).getId(), "Joke1", "Category", false)))
                .isPresent().get()
                .isEqualTo(new Joke(repository.getAll().get(0).getId(), "Joke1", "Category", false));
    }

    @Test
    void updateGivenNonExistingJokeReturnsEmpty() {
        Assertions.assertThat(
                repository.update(new Joke(1, "Joke", "Category", false)))
                .isEmpty();
    }

    @Test
    void deleteGivenExistingJokeReturnsTrue() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(repository.delete(repository.getAll().get(0).getId())).isTrue();
    }

    @Test
    void deleteGivenNonExistingJokeReturnsFalse() {
        Assertions.assertThat(repository.delete(1)).isFalse();
    }

    @Test
    void getAllUnconfirmedJokesReturnsListJokes() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(repository.getAllUnconfirmedJokes())
                .hasSize(1)
                .contains(new Joke(repository.getAll().get(0).getId(), "Joke", "Category", false));
    }

    @Test
    void getAllUnconfirmedWithEmptyDatabaseJokesReturnsEmpty() {
        Assertions.assertThat(repository.getAllUnconfirmedJokes())
                .isEmpty();
    }

    @Test
    void confirmJokeReturnsJoke() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(repository.confirmJoke(repository.getAll().get(0).getId()))
                .isEqualTo(Optional.of(new Joke(repository.getAll().get(0).getId(), "Joke", "Category", true)));
    }

    @Test
    void confirmGivenNonExistingJokeReturnsEmpty() {
        Assertions.assertThat(repository.confirmJoke(1)).isEmpty();
    }
}
