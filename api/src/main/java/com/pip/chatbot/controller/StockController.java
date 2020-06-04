package com.pip.chatbot.controller;

import com.pip.chatbot.model.finance.Stock;
import com.pip.chatbot.model.finance.StockApi;
import com.pip.chatbot.service.fortune.StockService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;
    private final ModelMapper modelMapper;
    private final DateTimeFormatter dateTimeFormatter;

    @GetMapping("/{symbol}")
    public ResponseEntity<StockApi> getStockForDay(@PathVariable String symbol, @RequestParam Optional<String> dateParam) {
        StockApi stockApi = modelMapper.map(stockService.getForDay(symbol,
                dateParam.map(s -> LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .orElseGet(LocalDateTime::now)), StockApi.class);

        return ResponseEntity
                .ok()
                .body(stockApi);
    }

    @GetMapping("/period/{symbol}")
    public ResponseEntity<List<Stock>> getStocksForPeriod(@PathVariable String symbol, @RequestParam String startDateParam, @RequestParam Optional<String> endDateParam) {
        LocalDateTime startDate = LocalDateTime.parse(startDateParam, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return ResponseEntity
                .ok()
                .body(stockService.getForPeriod(symbol, startDate,  endDateParam.map(s ->
                        LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .orElseGet(LocalDateTime::now)));
    }

    @GetMapping("/prediction/{symbol}")
    public ResponseEntity<List<Stock>> getPredictedForDays(@PathVariable String symbol) {
        return ResponseEntity
                .ok()
                .body(stockService.getPredictedForDays(symbol));
    }
}
