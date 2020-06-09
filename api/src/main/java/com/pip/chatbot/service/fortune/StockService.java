package com.pip.chatbot.service.fortune;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.StockErrorMessages;
import com.pip.chatbot.model.fortune.Stock;
import com.pip.chatbot.repository.fortune.StockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public Stock get(Long id) {
        return stockRepository.get(id)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(StockErrorMessages.NOT_FOUND).build());
    }

    public Stock getForDay(String symbol, LocalDate date) {
        return stockRepository.getCurrencyForDay(symbol, date)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(StockErrorMessages.NOT_FOUND).build());
    }

    public List<Stock> getForPeriod(String symbol, LocalDate startDay, LocalDate endDay) {
        return stockRepository.getCurrenciesForPeriod(symbol, startDay, endDay);
    }

    public List<Stock> getPredictedForDays(String symbol) {
        return stockRepository.getPredictedForDays(symbol);
    }
}
