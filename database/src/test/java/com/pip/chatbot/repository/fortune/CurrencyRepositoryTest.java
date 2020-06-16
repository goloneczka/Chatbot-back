package com.pip.chatbot.repository.fortune;

import com.pip.chatbot.jooq.fortune.tables.Currency;
import com.pip.chatbot.repository.DslContextFactory;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CurrencyRepositoryTest {

    private DSLContext context;
    private CurrencyRepository repository;

    @BeforeEach
    public void init() {
        context = new DslContextFactory().getDslContext();
        repository = new CurrencyRepository(context);
    }

    @AfterEach
    public void cleanUp() {
        context.truncate(Currency.CURRENCY).execute();
    }

    @Test
    public void createCreatesCurrency() {
        var currency = "currency";

        repository.create(currency);

        Assertions.assertThat(repository.getAll())
                .contains(currency);
    }

    @Test
    public void deleteDeletesCurrency() {
        var currency = "some currency";

        repository.create(currency);
        Assertions.assertThat(repository.getAll())
                .contains(currency);

        repository.delete(currency);
        Assertions.assertThat(repository.getAll()).isEmpty();
        ;
    }

    @Test
    public void getAllReturnsAllCurrencies() {
        var currencies = List.of("PLN", "USD");

        currencies.forEach(repository::create);

        Assertions.assertThat(repository.getAll())
                .isEqualTo(currencies);
    }
}
