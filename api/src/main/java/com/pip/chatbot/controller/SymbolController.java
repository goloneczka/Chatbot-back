package com.pip.chatbot.controller;

import com.pip.chatbot.model.finance.Symbol;
import com.pip.chatbot.service.finance.SymbolService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/symbols")
public class SymbolController {
    private final SymbolService symbolService;

    @GetMapping("/currencies")
    public ResponseEntity<List<Symbol>> getAllCurrencies() {
        return ResponseEntity
                .ok()
                .body(symbolService.getAllCurrencies());
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Symbol>> getAllCompanies() {
        return ResponseEntity
                .ok()
                .body(symbolService.getAllCompanies());
    }
}
