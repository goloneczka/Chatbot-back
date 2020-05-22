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
    public void init() throws ClassNotFoundException, IOException {
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
    void getAllReturnListJokes() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(repository.getAll())
                .isNotEmpty()
                .hasSize(1)
                .isEqualTo(
                        Collections.singletonList(
                                new Joke(repository.getAll().get(0).getId(), "Joke", "Category", false)
                        )
                );
    }

    @Test
    void getAllReturnEmptyList() {
        Assertions.assertThat(repository.getAll())
                .hasSize(0)
                .isEmpty();
    }

    @Test
    void getReturnJoke() {
        Assertions.assertThat(repository.create(new Joke(1, "Joke", "Category", true)))
                .isInstanceOf(Joke.class)
                .isEqualTo(repository.getAll().get(0));
    }


    @Test
    void updateReturnJoke() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(
                repository.update(new Joke(repository.getAll().get(0).getId(), "Joke1", "Category", false)))
                .isNotNull()
                .isEqualTo(Optional.of(new Joke(repository.getAll().get(0).getId(), "Joke1", "Category", false)));
    }

    @Test
    void updateReturnEmpty() {
        Assertions.assertThat(
                repository.update(new Joke(1, "Joke", "Category", false)))
                .isEmpty();
    }

    @Test
    void deleteReturnBooleanTrue() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(repository.delete(repository.getAll().get(0).getId())).isTrue();
    }

    @Test
    void deleteReturnBooleanFalse() {
        Assertions.assertThat(repository.delete(1)).isFalse();
    }

    @Test
    void getAllUnconfirmedJokesReturnListJokes() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(repository.getAllUnconfirmedJokes())
                .hasSize(1);
    }

    @Test
    void getAllUnconfirmedJokesReturnEmpty() {
        Assertions.assertThat(repository.getAllUnconfirmedJokes())
                .hasSize(0)
                .isEmpty();
    }

    @Test
    void confirmJokeReturnJoke() {
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", false));

        Assertions.assertThat(repository.confirmJoke(repository.getAll().get(0).getId()).get().isConfirmed())
                .isTrue();
    }

    @Test
    void confirmJokeReturnEmpty() {
        Assertions.assertThat(repository.confirmJoke(1))
                .isEmpty();
    }
}
