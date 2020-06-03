package com.pip.chatbot.repository.finance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.model.finance.Stock;
import com.pip.chatbot.model.finance.Symbol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StockRepository {
    List<Stock> stocks;

    public StockRepository(){
            stocks = Arrays.asList(
                    new Stock(1, new Symbol("EURUSD=X", "Euro to USD", true), (float) 1.1078, LocalDate.parse("2020-05-29"), true),
                    new Stock(2, new Symbol("EURUSD=X", "Euro to USD", true), (float) 1.1115, LocalDate.parse("2020-06-01"), true),
                    new Stock(3, new Symbol("EURUSD=X", "Euro to USD", true), (float) 1.1135, LocalDate.parse("2020-06-02"), true),
                    new Stock(4, new Symbol("OTGLF", "CD Projekt S.A.", false), (float) 100.75, LocalDate.parse("2020-05-29"), true),
                    new Stock(5, new Symbol("OTGLF", "CD Projekt S.A.", false), (float) 96.50, LocalDate.parse("2020-06-01"), true),
                    new Stock(6, new Symbol("OTGLF", "CD Projekt S.A.", false), (float) 98.08, LocalDate.parse("2020-06-02"), true));
    }

    public Optional<Stock> get(int id) {
        Stock resultStock = null;
        for (Stock stock : stocks) {
            if (stock.getId() == id)
                resultStock = stock;
        }
        return Optional.ofNullable(resultStock);
    }

    public Optional<Stock> getCurrencyForDay(String symbol, LocalDate date) {
        Stock resultStock = null;
        for (Stock stock : stocks) {
            if (stock.getSymbol().getSymbol().equals(symbol) && stock.getDate().getDayOfMonth() == date.getDayOfMonth())
                resultStock = stock;
        }
        return Optional.ofNullable(resultStock);
    }

    public List<Stock> getCurrenciesForPeriod(String symbol, LocalDate startDay, LocalDate endDay) {
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
            if (stock.getSymbol().getSymbol().equals(symbol) && stock.getDate().isAfter(LocalDate.now()) && stock.getDate().isBefore(LocalDate.now().plusDays(daysNumber)))
                resultStocks.add(stock);
        }
        return resultStocks;
    }
}
