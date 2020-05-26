package com.pip.chatbot.repository.joke;

import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.model.joke.Mark;
import com.pip.chatbot.repository.DslContextFactory;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;
import static com.pip.chatbot.jooq.jokes.tables.Joke.JOKE;

public class JokesRepositoryTest {

    private JokesRepository repository;
    private DSLContext dslContext;
    private AdminJokesRepository adminJokesRepository;

    @BeforeEach
    public void init() {
        ModelMapper modelMapper = new ModelMapper();
        DslContextFactory dslContextFactory = new DslContextFactory();

        dslContext = dslContextFactory.getDslContext();
        CategoriesRepository categoriesRepository = new CategoriesRepository(dslContext, modelMapper);

        Category category = new Category("Category", true);
        adminJokesRepository = new AdminJokesRepository(dslContext, modelMapper);
        categoriesRepository.create(category);
        repository = new JokesRepository(dslContext);
    }

    @AfterEach
    public void truncateTable() {
        dslContext.truncateTable(JOKE).cascade().execute();
        dslContext.truncateTable(CATEGORY).cascade().execute();
    }

    @Test
    public void getRandomJokeReturnsJoke() {
        Joke joke = new Joke(1, "Joke", "Category", true);
        repository.createTemporaryJoke(joke);

        Assertions.assertThat(repository.getRandomJoke())
                .isNotEmpty()
                .isEqualTo(Optional.of(new Joke(adminJokesRepository.getAll().get(0).getId(),
                        joke.getJoke(), joke.getCategory(), joke.isConfirmed())));
    }

    @Test
    void getRandomJokeWihEmptyDatabaseReturnsEmpty() {
        Assertions.assertThat(repository.getRandomJoke())
                .isEmpty();
    }

    @Test
    void getRandomJokeByCategoryReturnsJoke() {
        Joke joke = new Joke(1, "Joke", "Category", true);
        repository.createTemporaryJoke(joke);

        Assertions.assertThat(repository.getRandomJokeByCategory("Category"))
                .isNotEmpty()
                .isEqualTo(Optional.of(new Joke(adminJokesRepository.getAll().get(0).getId(),
                        joke.getJoke(), joke.getCategory(), joke.isConfirmed())));
    }

    @Test
    void getRandomJokeByNonExistingCategoryReturnsEmpty() {
        Assertions.assertThat(repository.getRandomJokeByCategory("Category1"))
                .isEmpty();
    }

    @Test
    void getAllCategoriesReturnsListCategories() {
        Assertions.assertThat(repository.getAllCategories())
                .isNotEmpty()
                .contains(new Category("Category", true));
    }

    @Test
    void getAllConfirmedCategoriesReturnsListCategory() {
        Assertions.assertThat(repository.getAllConfirmedCategories())
                .isNotEmpty()
                .hasSize(1)
                .contains(new Category("Category", true));
    }

    @Test
    void createTemporaryJokeReturnsJoke() {
        Joke joke = new Joke(1, "Joke", "Category", false);

        Assertions.assertThat(repository.createTemporaryJoke(joke))
                .isEqualTo(
                        Optional.of(new Joke(adminJokesRepository.getAll().get(0).getId(),
                                joke.getJoke(),
                                joke.getCategory(),
                                joke.isConfirmed())));
    }

    @Test
    void createTemporaryCategoryReturnsCategory() {
        Assertions.assertThat(repository.createTemporaryCategory("Category1"))
                .isNotEmpty()
                .isEqualTo(Optional.of(new Category("Category1", true)));
    }

    @Test
    void createMarkReturnsMark() {
        Joke joke = new Joke(1, "Joke", "Category", false);
        repository.createTemporaryJoke(joke);

        Assertions.assertThat(repository.createMark(new Mark(1, adminJokesRepository.getAll().get(0).getId(), 4.5))
                .getMark())
                .isEqualTo(4.5);
    }

    @Test
    void getAvgJokeMarkReturnsMark() {
        Joke joke = new Joke(1, "Joke", "Category", true);
        repository.createTemporaryJoke(joke);
        repository.createMark(new Mark(1, repository.getRandomJoke().get().getId(), 5.0));
        repository.createMark(new Mark(1, repository.getRandomJoke().get().getId(), 4.0));

        Assertions.assertThat(repository.getAvgJokeMark(adminJokesRepository.getAll().get(0).getId().toString()))
                .isNotEmpty()
                .isEqualTo(Optional.of(new Mark(null,adminJokesRepository.getAll().get(0).getId(),4.5)));
    }

    @Test
    void getAvgJokeMarkGivenNonExistingJokeReturnsEmpty() {
        Assertions.assertThat(repository.getAvgJokeMark("1"))
                .isEmpty();
    }
}

