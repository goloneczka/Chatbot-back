package com.pip.chatbot.controller;

import com.pip.chatbot.model.finance.SymbolApi;
import com.pip.chatbot.service.fortune.SymbolService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/symbols")
public class SymbolController {
    private final SymbolService symbolService;
    private final ModelMapper modelMapper;

    @GetMapping("/currencies")
    public ResponseEntity<List<SymbolApi>> getAllCurrencies() {
        List<SymbolApi> symbolApiList = modelMapper.map(symbolService.getAllCurrencies(), new TypeToken<List<SymbolApi>>() {
        }.getType());

        return ResponseEntity
                .ok()
                .body(symbolApiList);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<SymbolApi>> getAllCompanies() {
        List<SymbolApi> symbolApiList = modelMapper.map(symbolService.getAllCompanies(), new TypeToken<List<SymbolApi>>() {
        }.getType());

        return ResponseEntity
                .ok()
                .body(symbolApiList);
    }
}
