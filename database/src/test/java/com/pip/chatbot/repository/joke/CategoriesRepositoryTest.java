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

import java.util.Collections;
import java.util.Optional;

import static com.pip.chatbot.jooq.jokes.tables.Category.CATEGORY;

public class CategoriesRepositoryTest {

    private CategoriesRepository repository;
    private DSLContext dslContext;
    private AdminJokesRepository adminJokesRepository;

    @BeforeEach
    public void init() {
        ModelMapper modelMapper = new ModelMapper();
        DslContextFactory dslContextFactory = new DslContextFactory();


        dslContext = dslContextFactory.getDslContext().dsl();
        adminJokesRepository = new AdminJokesRepository(dslContext, modelMapper);
        repository = new CategoriesRepository(dslContext, modelMapper);
    }

    @AfterEach
    public void truncateTable() {
        dslContext.truncateTable(CATEGORY).cascade().execute();
    }


    @Test
    public void getAllReturnsListCategories() {
        Category category = new Category("Category", true);
        repository.create(category);

        Assertions.assertThat(repository.getAll()).isNotEmpty().contains(category);
    }

    @Test
    void getAllWithEmptyDatabaseReturnsEmpty() {
        Assertions.assertThat(repository.getAll())
                .isEmpty();
    }

    @Test
    public void getJokesForCategoryReturnsListJoke() {
        Category category = new Category("Category", true);
        repository.create(category);
        JokesRepository jokesRepository = new JokesRepository(dslContext);
        jokesRepository.createTemporaryJoke(new Joke(1, "Joke", "Category", true));

        Assertions.assertThat(repository.getJokesForCategory("Category"))
                .hasSize(1)
                .isEqualTo(
                        Collections.singletonList(new Joke(
                                adminJokesRepository.getAll().get(0).getId(), "Joke", "Category", true)));

    }

    @Test
    void getJokesForCategoryWithEmptyDatabaseReturnsEmpty() {
        Category category = new Category("Category", true);
        repository.create(category);
        Assertions.assertThat(repository.getJokesForCategory("Category"))
                .isEmpty();
    }

    @Test
    void getJokesForCategoryNotExistsReturnsEmpty() {
        Assertions.assertThat(repository.getJokesForCategory("Category"))
                .isEmpty();
    }

    @Test
    public void createReturnsCategory() {
        Category category = new Category("Category", true);

        Assertions.assertThat(repository.create(category))
                .isNotNull()
                .isEqualTo(category);
    }

    @Test
    public void updateReturnsCategory() {
        Category category = new Category("Category", true);
        repository.create(category);

        Assertions.assertThat(repository.update(category.getCategory(), "Category1"))
                .isNotNull()
                .isEqualTo(Optional.of(new Category("Category1", true)));

    }

    @Test
    void updateGivenNonExistingCategoryReturnsEmpty() {
        Assertions.assertThat(repository.update("Category", "Category1"))
                .isEmpty();
    }

    @Test
    public void deleteReturnsTrue() {
        Category category = new Category("Category", true);
        repository.create(category);
        Assertions.assertThat(repository.delete(category.getCategory())).isTrue();
    }

    @Test
    void deleteGivenNonExistingCategoryReturnsFalse() {
        Assertions.assertThat(repository.delete("Category1")).isFalse();
    }
}
