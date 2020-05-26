package com.pip.chatbot.controller;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.StockErrorMessages;
import com.pip.chatbot.model.finance.Stock;
import com.pip.chatbot.service.finance.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> getStockForDay(@PathVariable String symbol, @RequestBody LocalDateTime date) {
        return ResponseEntity
                .ok()
                .body(stockService.getCurrencyForDay(symbol, date));
    }

    @GetMapping("/period/{symbol}")
    public ResponseEntity<List<Stock>> getStocksForPeriod(@PathVariable String symbol, @RequestBody LocalDateTime startDay, @RequestBody LocalDateTime endDay) {
        return ResponseEntity
                .ok()
                .body(stockService.getCurrenciesForPeriod(symbol, startDay, endDay));
    }

    @GetMapping("/prediction/{symbol}")
    public ResponseEntity<List<Stock>> getPredictedForDays(@PathVariable String symbol, @RequestBody int daysNumber) {
        return ResponseEntity
                .ok()
                .body(stockService.getPredictedForDays(symbol, daysNumber));
    }
}
