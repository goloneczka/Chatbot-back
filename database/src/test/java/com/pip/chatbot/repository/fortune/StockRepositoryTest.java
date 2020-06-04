package com.pip.chatbot.repository.fortune;

import com.pip.chatbot.jooq.fortune.Tables;
import com.pip.chatbot.model.finance.Stock;
import com.pip.chatbot.model.finance.Symbol;
import com.pip.chatbot.repository.DslContextFactory;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StockRepositoryTest {

    private DSLContext dslContext;
    private StockRepository stockRepository;
    private Stock stock;
    private Symbol symbol;

    @BeforeEach
    void init() {
        DslContextFactory dslContextFactory = new DslContextFactory();
        this.dslContext = dslContextFactory.getDslContext();
        this.symbol = new Symbol("Symbol1", "name1", false);
        Symbol symbol1 = new Symbol("Symbol2", "name2", false);
        SymbolRepository symbolRepository = new SymbolRepository(dslContext);
        symbolRepository.createSymbol(symbol);
        symbolRepository.createSymbol(symbol1);
        this.stock = new Stock(1L, "Symbol1", 1.1078F, LocalDate.of(2020,5,20).atStartOfDay(), true);

        this.stockRepository = new StockRepository(dslContext);
    }

    @AfterEach
    void clearDatabase() {
        dslContext.truncateTable(Tables.STOCK).cascade().execute();
        dslContext.truncateTable(Tables.SYMBOL).cascade().execute();
    }

    void addStockToDatabase() {
        this.stockRepository.createStock(stock);
        stockRepository.createStock(new Stock(2L, "Symbol1", 1.1078F, LocalDate.of(2020,5,25).atStartOfDay(), true));
        stockRepository.createStock(new Stock(3L, "Symbol1", 1.1078F, LocalDate.of(2020,5,30).atStartOfDay(), true));
        stockRepository.createStock(new Stock(4L, "Symbol2", 1.1078F, LocalDate.of(2020,6,4).atStartOfDay(), true));
        stockRepository.createStock(new Stock(5L, "Symbol1", 1.1078F, LocalDate.of(2020,6,10).atStartOfDay(), true));
    }

    @Test
    void createStockReturnsStock() {
        Assertions.assertThat(stockRepository.createStock(stock))
                .isEqualTo(stock);

        Assertions.assertThat(stockRepository.get(stock.getId()))
                .get()
                .isEqualTo(stock);
    }

    @Test
    void getStockReturnsStock() {
        this.addStockToDatabase();

        Assertions.assertThat(stockRepository.get(stock.getId()))
                .get()
                .isEqualTo(stock);
    }

    @Test
    void getStockGivenNonExistingStockReturnsEmpty() {
        Assertions.assertThat(stockRepository.get(stock.getId()))
                .isEmpty();
    }

    @Test
    void getCurrencyForDayReturnsListOfStocks() {
        this.addStockToDatabase();

        Assertions.assertThat(stockRepository.getCurrencyForDay(symbol.getSymbol(), stock.getDate()))
                .get()
                .isEqualTo(stock);
    }

    @Test
    void getCurrencyForDayGivenNonExistingSymbolReturnsEmpty() {
        this.addStockToDatabase();

        Assertions.assertThat(stockRepository.getCurrencyForDay("NonExistingSymbol", stock.getDate()))
                .isEmpty();
    }

    @Test
    void getCurrencyForDayGivenWrongDateReturnsEmpty() {
        this.addStockToDatabase();

        Assertions.assertThat(stockRepository.getCurrencyForDay("NonExistingSymbol", LocalDate.of(2010,5,20).atStartOfDay()))
                .isEmpty();
    }

    @Test
    void getCurrenciesForPeriodReturnsListOfStocks() {
        this.addStockToDatabase();

        Assertions.assertThat(stockRepository.getCurrenciesForPeriod(symbol.getSymbol(),stock.getDate(),stock.getDate().plusDays(7)))
                .hasSize(2)
                .contains(stock);
    }

    @Test
    void getCurrenciesForPeriodGivenNonExistingSymbolReturnsEmptyListOfStocks() {
        this.addStockToDatabase();

        Assertions.assertThat(stockRepository.getCurrenciesForPeriod("NonExistingSymbol",stock.getDate(),stock.getDate().plusDays(7)))
                .isEmpty();
    }

    @Test
    void getCurrenciesForPeriodGivenWrongPeriodReturnsEmptyListOfStocks() {
        this.addStockToDatabase();

        Assertions.assertThat(stockRepository.getCurrenciesForPeriod("NonExistingSymbol",stock.getDate().plusYears(1),stock.getDate().plusYears(1).plusDays(7)))
                .isEmpty();
    }

    @Test
    void getPredictedForDaysReturnsListOfStocks() {
        stockRepository.createStock(new Stock(4L, "Symbol1", 1.1078F, LocalDateTime.now().plusDays(1),true));

        Assertions.assertThat(stockRepository.getPredictedForDays(symbol.getSymbol()))
                .hasSize(1);
    }
}
