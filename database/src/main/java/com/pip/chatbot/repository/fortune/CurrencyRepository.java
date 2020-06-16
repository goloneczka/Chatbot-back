package com.pip.chatbot.repository.fortune;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.util.List;

import static com.pip.chatbot.jooq.fortune.tables.Currency.CURRENCY;

@RequiredArgsConstructor
public class CurrencyRepository {

    private final DSLContext context;

    public void create(String currency) {
        context.insertInto(CURRENCY).values(currency).execute();
    }

    public boolean delete(String currency) {
        return 0 < context.deleteFrom(CURRENCY).where(CURRENCY.NAME.eq(currency))
                .execute();
    }

    public List<String> getAll() {
        return context.select(CURRENCY.NAME).from(CURRENCY).fetch(CURRENCY.NAME);
    }
}
