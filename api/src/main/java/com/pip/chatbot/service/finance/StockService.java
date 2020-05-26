package com.pip.chatbot.service.finance;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.StockErrorMessages;
import com.pip.chatbot.model.finance.Stock;
import com.pip.chatbot.repository.finance.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    @Value("${application.finance.maximumPredictionDays}")
    private Integer maximumPredictionDays;

    public Stock get(int id) {
        return stockRepository.get(id)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(StockErrorMessages.NOT_FOUND).build());
    }

    public Stock getCurrencyForDay(String symbol, LocalDateTime date) {
        return stockRepository.getCurrencyForDay(symbol, date)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(StockErrorMessages.NOT_FOUND).build());
    }

    public List<Stock> getCurrenciesForPeriod(String symbol, LocalDateTime startDay, LocalDateTime endDay) {
        return stockRepository.getCurrenciesForPeriod(symbol, startDay, endDay);
    }

    public List<Stock> getPredictedForDays(String symbol, int daysNumber) {
        if (daysNumber > maximumPredictionDays) {
            throw new ChatbotExceptionBuilder().addError(StockErrorMessages.DAYS_NUMBER_TOO_HIGH).build();
        }
        return stockRepository.getPredictedForDays(symbol, daysNumber);
    }
}
