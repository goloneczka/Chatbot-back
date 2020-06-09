package com.pip.chatbot.repository.fortune;

import com.pip.chatbot.model.fortune.Stock;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.pip.chatbot.jooq.fortune.tables.Stock.STOCK;

@AllArgsConstructor
public class StockRepository {

    private DSLContext dsl;

    public Optional<Stock> get(Long id) {
        return dsl.selectFrom(STOCK)
                .where(STOCK.ID.eq(id))
                .fetchOptionalInto(Stock.class);
    }

    public Stock createStock(Stock stock) {
        return dsl.insertInto(STOCK)
                .set(STOCK.ID, stock.getId())
                .set(STOCK.SYMBOL, stock.getSymbol())
                .set(STOCK.VALUE, stock.getValue())
                .set(STOCK.DATE, stock.getDate())
                .set(STOCK.IS_HISTORICAL, stock.isHistorical())
                .returning()
                .fetchOne()
                .into(Stock.class);
    }

    public Optional<Stock> getCurrencyForDay(String symbol, LocalDate date) {
        return dsl.selectFrom(STOCK)
                .where(STOCK.SYMBOL.eq(symbol))
                .and(STOCK.DATE.eq(date))
                .and(STOCK.IS_HISTORICAL.eq(true))
                .fetchOptionalInto(Stock.class);
    }

    public List<Stock> getCurrenciesForPeriod(String symbol, LocalDate startDay, LocalDate endDay) {
                return dsl.selectFrom(STOCK)
                        .where(STOCK.SYMBOL.eq(symbol))
                        .and(STOCK.DATE.between(startDay, endDay))
                        .and(STOCK.IS_HISTORICAL.eq(true))
                        .fetchInto(Stock.class);
    }

    public List<Stock> getPredictedForDays(String symbol) {
                return dsl.selectFrom(STOCK)
                        .where(STOCK.SYMBOL.eq(symbol))
                        .and(STOCK.DATE.greaterThan(LocalDate.now()))
                        .and(STOCK.IS_HISTORICAL.eq(false))
                        .fetchInto(Stock.class);
    }
}
