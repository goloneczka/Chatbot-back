package com.pip.chatbot.repository.joke;

import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.model.joke.Mark;
import com.pip.chatbot.model.joke.MarkApi;
import com.pip.chatbot.repository.DslContextFactory;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;
import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;

public class JokesRepositoryTest {

    private JokesRepository repository;
    private DSLContext dslContext;
    private AdminJokesRepository adminJokesRepository;

    @BeforeEach
    public void init() throws ClassNotFoundException, IOException {
        ModelMapper modelMapper = new ModelMapper();
        DslContextFactory dslContextFactory = new DslContextFactory();
        dslContext = dslContextFactory.getDslContext();
        CategoriesRepository categoriesRepository = new CategoriesRepository(dslContext, modelMapper);
        adminJokesRepository = new AdminJokesRepository(dslContext, modelMapper);
        Category category = new Category("Category", true);
        categoriesRepository.create(category);


        repository = new JokesRepository(dslContext);
    }

    @AfterEach
    public void truncateTable() {
        dslContext.truncateTable(JOKE).cascade().execute();
        dslContext.truncateTable(CATEGORY).cascade().execute();
    }

    @Test
    public void getRandomJokeReturnJoke() {
        Joke joke = new Joke(1, "Joke", "Category", true);
        repository.createTemporaryJoke(joke);

        Assertions.assertThat(repository.getRandomJoke())
                .isNotEmpty()
                .isEqualTo(Optional.of(new Joke(adminJokesRepository.getAll().get(0).getId(),
                        joke.getJoke(), joke.getCategory(), joke.isConfirmed())));
    }

    @Test
    void getRandomJokeReturnEmpty() {
        Assertions.assertThat(repository.getRandomJoke())
                .isEmpty();
    }

    @Test
    void getRandomJokeByCategoryReturnJoke() {
        Joke joke = new Joke(1, "Joke", "Category", true);
        repository.createTemporaryJoke(joke);

        Assertions.assertThat(repository.getRandomJokeByCategory("Category"))
                .isNotEmpty()
                .isEqualTo(Optional.of(new Joke(adminJokesRepository.getAll().get(0).getId(),
                        joke.getJoke(), joke.getCategory(), joke.isConfirmed())));
    }

    @Test
    void getRandomJokeByCategoryReturnEmpty() {
        Assertions.assertThat(repository.getRandomJokeByCategory("Category1"))
                .isEmpty();
    }

    @Test
    void getAllCategoriesReturnListCategories() {
        Assertions.assertThat(repository.getAllCategories())
                .isNotEmpty()
                .contains(new Category("Category", true));
    }

    @Test
    void getAllConfirmedCategoriesReturnListCategory() {
        Assertions.assertThat(repository.getAllConfirmedCategories())
                .isNotEmpty()
                .hasSize(1)
                .contains(new Category("Category", true));
    }

    @Test
    void createTemporaryJokeReturnJoke() {
        Joke joke = new Joke(1, "Joke", "Category", false);

        Assertions.assertThat(repository.createTemporaryJoke(joke))
                .isNotEmpty()
                .isEqualTo(
                        Optional.of(new Joke(adminJokesRepository.getAll().get(0).getId(),
                                joke.getJoke(),
                                joke.getCategory(),
                                joke.isConfirmed())));
    }

    @Test
    void createTemporaryCategoryReturnCategory() {
        Assertions.assertThat(repository.createTemporaryCategory("Category1"))
                .isNotEmpty()
                .isEqualTo(Optional.of(new Category("Category1", true)));
    }

    @Test
    void createMarkReturnMark() {
        Joke joke = new Joke(1, "Joke", "Category", false);
        repository.createTemporaryJoke(joke);

        Assertions.assertThat(repository.createMark(new Mark(1, adminJokesRepository.getAll().get(0).getId(), 4.5)).get().getMark())
                .isEqualTo(4.5);
    }

    @Test
    void getAvgJokeMarkReturnMarkApi() {
        Joke joke = new Joke(1, "Joke", "Category", true);
        repository.createTemporaryJoke(joke);
        repository.createMark(new Mark(1, repository.getRandomJoke().get().getId(), 5.0));
        repository.createMark(new Mark(1, repository.getRandomJoke().get().getId(), 4.0));

        Assertions.assertThat(repository.getAvgJokeMark(adminJokesRepository.getAll().get(0).getId().toString()))
                .isNotEmpty()
                .isEqualTo(Optional.of(new MarkApi(adminJokesRepository.getAll().get(0).getId(),4.5)));
    }

    @Test
    void getAvgJokeMarkReturnEmpty() {
        Assertions.assertThat(repository.getAvgJokeMark("1"))
                .isEmpty();
    }
}

