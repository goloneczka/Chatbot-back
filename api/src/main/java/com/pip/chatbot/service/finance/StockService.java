package com.pip.chatbot.service.finance;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.StockErrorMessages;
import com.pip.chatbot.model.finance.Stock;
import com.pip.chatbot.repository.finance.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    @Value("${application.finance.predictionDaysNumber}")
    private Integer predictionDaysNumber;

    public Stock get(int id) {
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
        return stockRepository.getPredictedForDays(symbol, predictionDaysNumber);
    }
}
