package com.pip.chatbot.controller;

import com.pip.chatbot.model.finance.Symbol;
import com.pip.chatbot.model.finance.SymbolApi;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.service.fortune.SymbolService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/symbols")
public class SymbolController {
    private final SymbolService symbolService;


    @PostMapping
    public ResponseEntity<Symbol> createSymbol(@RequestBody Symbol symbol){
        return ResponseEntity.ok().body(symbolService.createSymbol(symbol));
    }


    @PutMapping
    public ResponseEntity<Symbol> updateSymbol(@RequestBody Symbol symbol){
        return ResponseEntity.ok().body(symbolService.updateSymbol(symbol));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSymbol(@RequestBody String id){
        symbolService.deleteSymbol(id);
        return Response.SUCCESS;
    }

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
