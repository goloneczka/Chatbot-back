package com.pip.chatbot.repository.fortune;

import com.pip.chatbot.model.fortune.Symbol;
import com.pip.chatbot.repository.DslContextFactory;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.pip.chatbot.jooq.fortune.tables.Symbol.SYMBOL;

public class SymbolRepositoryTest {

    private DSLContext dslContext;
    private SymbolRepository symbolRepository;
    private Symbol symbolCurrency;
    private Symbol symbolExchange;

    @BeforeEach
    void init(){
        DslContextFactory dslContextFactory = new DslContextFactory();
        this.dslContext = dslContextFactory.getDslContext();
        this.symbolCurrency = new Symbol("SymbolCurrency","SymbolCurrency",true);
        this.symbolExchange = new Symbol("SymbolExchange","SymbolExchange",false);

        this.symbolRepository = new SymbolRepository(dslContext);
    }

    @AfterEach
    void clearDatabase(){
        dslContext.truncateTable(SYMBOL).cascade().execute();
    }

    void addSymbolToDatabase(){
        symbolRepository.createSymbol(this.symbolCurrency);
        symbolRepository.createSymbol(this.symbolExchange);
    }

    @Test
    void createSymbolReturnsSymbol() {
        Assertions.assertThat(symbolRepository.createSymbol(this.symbolExchange))
                .isEqualTo(this.symbolExchange);

        Assertions.assertThat(symbolRepository.createSymbol(this.symbolCurrency))
                .isEqualTo(this.symbolCurrency);

    }

    @Test
    void updateSymbolReturnsUpdatedSymbol() {
        this.addSymbolToDatabase();
        Symbol symbolUpdated = new Symbol("SymbolCurrency","SymbolCurrencyUpdated",true);

        Assertions.assertThat(symbolRepository.updateSymbol(symbolUpdated))
                .get()
                .isEqualTo(symbolUpdated);

        Assertions.assertThat(symbolRepository.getAllCurrencies())
                .hasSize(1)
                .contains(symbolUpdated);

    }

    @Test
    void updateSymbolGivenNonExistingSymbolReturnsEmpty() {
        Assertions.assertThat(symbolRepository.updateSymbol(this.symbolCurrency))
                .isEmpty();
    }

    @Test
    void deleteSymbolReturnsTrue() {
        this.addSymbolToDatabase();

        Assertions.assertThat(symbolRepository.deleteSymbol("SymbolExchange"))
                .isTrue();

        Assertions.assertThat(symbolRepository.getAllCompanies())
                .isEmpty();
    }

    @Test
    void deleteSymbolReturnsFalse() {
        this.addSymbolToDatabase();

        Assertions.assertThat(symbolRepository.deleteSymbol("NonExistingSymbolExchange"))
                .isFalse();
    }


    @Test
    void getAllCurrenciesReturnsListOfCurrenciesSymbols() {
        this.addSymbolToDatabase();

        Assertions.assertThat(symbolRepository.getAllCurrencies())
                .hasSize(1)
                .contains(this.symbolCurrency);
    }

    @Test
    void getAllCurrenciesReturnsEmptyListOfCurrenciesSymbols() {
        Assertions.assertThat(symbolRepository.getAllCurrencies())
                .isEmpty();
    }

    @Test
    void getAllCompaniesReturnsListOfCompaniesSymbols() {
        this.addSymbolToDatabase();

        Assertions.assertThat(symbolRepository.getAllCompanies())
                .hasSize(1)
                .contains(this.symbolExchange);
    }

    @Test
    void getAllCompaniesReturnsEmptyListOfCompaniesSymbols() {
        Assertions.assertThat(symbolRepository.getAllCompanies())
                .isEmpty();
    }
}
