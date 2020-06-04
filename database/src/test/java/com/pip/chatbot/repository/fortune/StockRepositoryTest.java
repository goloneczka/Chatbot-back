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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockRepositoryTest {

    private DSLContext dslContext;
    private StockRepository stockRepository;
    private Stock stock;
    private Symbol symbol;
    private DateTimeFormatter formatter;

    @BeforeEach
    void init() {
        DslContextFactory dslContextFactory = new DslContextFactory();
        this.dslContext = dslContextFactory.getDslContext();
        this.symbol = new Symbol("Symbol1", "name1", true);
        Symbol symbol1 = new Symbol("Symbol2", "name2", false);
        SymbolRepository symbolRepository = new SymbolRepository(dslContext);
        symbolRepository.createSymbol(symbol);
        symbolRepository.createSymbol(symbol1);
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.stock = new Stock(1L, "Symbol1", 1.1078F, LocalDateTime.parse("2020-05-20 00:00", formatter), true);

        this.stockRepository = new StockRepository(dslContext);
    }

    @AfterEach
    void clearDatabase() {
        dslContext.truncateTable(Tables.STOCK).cascade().execute();
        dslContext.truncateTable(Tables.SYMBOL).cascade().execute();
    }

    void addStockToDatabase() {
        this.stockRepository.createStock(stock);
        stockRepository.createStock(new Stock(2L, "Symbol1", 1.1078F, LocalDateTime.parse("2020-05-25 00:00", formatter), true));
        stockRepository.createStock(new Stock(3L, "Symbol1", 1.1078F, LocalDateTime.parse("2020-05-30 00:00", formatter), true));
        stockRepository.createStock(new Stock(4L, "Symbol2", 1.1078F, LocalDateTime.parse("2020-06-10 00:00", formatter), true));
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

        Assertions.assertThat(stockRepository.getCurrencyForDay("NonExistingSymbol", LocalDateTime.parse("2010-05-30 00:00", formatter)))
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
