package com.pip.chatbot.repository.finance;

import com.pip.chatbot.model.finance.Stock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockRepository {
    List<Stock> stocks;

    public StockRepository() {
        stocks = new ArrayList<>();
    }

    public Optional<Stock> get(int id) {
        Stock resultStock = null;
        for (Stock stock : stocks) {
            if (stock.getId() == id)
                resultStock = stock;
        }
        return Optional.ofNullable(resultStock);
    }

    public Optional<Stock> getCurrencyForDay(String symbol, LocalDateTime date) {
        Stock resultStock = null;
        for (Stock stock : stocks) {
            if (stock.getSymbol().getSymbol().equals(symbol) && stock.getDate().getDayOfMonth() == date.getDayOfMonth())
                resultStock = stock;
        }
        return Optional.ofNullable(resultStock);
    }

    public List<Stock> getCurrenciesForPeriod(String symbol, LocalDateTime startDay, LocalDateTime endDay) {
        List<Stock> resultStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (stock.getSymbol().getSymbol().equals(symbol) && stock.getDate().isAfter(startDay) && stock.getDate().isBefore(endDay))
                resultStocks.add(stock);
        }
        return resultStocks;
    }

    public List<Stock> getPredictedForDays(String symbol, int daysNumber){
        List<Stock> resultStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (stock.getSymbol().getSymbol().equals(symbol) && stock.getDate().isAfter(LocalDateTime.now()) && stock.getDate().isBefore(LocalDateTime.now().plusDays(daysNumber)))
                resultStocks.add(stock);
        }
        return resultStocks;
    }
}
