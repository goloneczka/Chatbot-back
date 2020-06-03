package com.pip.chatbot.controller;

import com.pip.chatbot.model.finance.Stock;
import com.pip.chatbot.model.finance.StockApi;
import com.pip.chatbot.service.finance.StockService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;
    private final ModelMapper modelMapper;

    @GetMapping("/{symbol}")
    public ResponseEntity<StockApi> getStockForDay(@PathVariable String symbol, @RequestParam Optional<String> dateParam) {
        LocalDate date;
        if (dateParam.isPresent()) {
            date = LocalDate.parse(dateParam.get(), DateTimeFormatter.ISO_DATE);
        } else {
            date = LocalDate.now();
        }

        StockApi stockApi = modelMapper.map(stockService.getForDay(symbol, date), StockApi.class);
        return ResponseEntity
                .ok()
                .body(stockApi);
    }

    @GetMapping("/period/{symbol}")
    public ResponseEntity<List<StockApi>> getStocksForPeriod(@PathVariable String symbol, @RequestParam String startDateParam, @RequestParam Optional<String> endDateParam) {
        LocalDate startDate = LocalDate.parse(startDateParam, DateTimeFormatter.ISO_DATE);

        LocalDate endDate;
        if (endDateParam.isPresent()) {
            endDate = LocalDate.parse(endDateParam.get(), DateTimeFormatter.ISO_DATE);
        } else {
            endDate = LocalDate.now();
        }

        List<StockApi> stockApiList = modelMapper.map(stockService.getForPeriod(symbol, startDate, endDate), new TypeToken<List<StockApi>>() {
        }.getType());

        return ResponseEntity
                .ok()
                .body(stockApiList);
    }

    @GetMapping("/prediction/{symbol}")
    public ResponseEntity<List<StockApi>> getPredictedForDays(@PathVariable String symbol) {
        List<StockApi> stockApiList = modelMapper.map(stockService.getPredictedForDays(symbol), new TypeToken<List<StockApi>>() {
        }.getType());

        return ResponseEntity
                .ok()
                .body(stockApiList);
    }
}
