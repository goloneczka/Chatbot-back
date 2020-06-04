package com.pip.chatbot.repository.fortune;

import com.pip.chatbot.model.finance.Stock;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Optional<Stock> getCurrencyForDay(String symbol, LocalDateTime date) {
        return dsl.selectFrom(STOCK)
                .where(STOCK.SYMBOL.eq(symbol))
                .and(STOCK.DATE.eq(date))
                .fetchOptionalInto(Stock.class);
    }

    public List<Stock> getCurrenciesForPeriod(String symbol, LocalDateTime startDay, LocalDateTime endDay) {
                return dsl.selectFrom(STOCK)
                        .where(STOCK.SYMBOL.eq(symbol))
                        .and(STOCK.DATE.between(startDay, endDay))
                        .fetchInto(Stock.class);
    }

    public List<Stock> getPredictedForDays(String symbol) {
                return dsl.selectFrom(STOCK)
                        .where(STOCK.SYMBOL.eq(symbol))
                        .and(STOCK.DATE.between(LocalDateTime.now(), LocalDateTime.now().plusDays(7)))
                        .fetchInto(Stock.class);
    }
}
