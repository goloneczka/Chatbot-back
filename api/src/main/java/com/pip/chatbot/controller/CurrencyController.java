package com.pip.chatbot.controller;

import com.pip.chatbot.model.Currency;
import com.pip.chatbot.model.fortune.Symbol;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.service.fortune.CurrencyService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Currency currency) {
        return ResponseEntity.ok().body(service.create(currency.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return Response.SUCCESS;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAll() {
        return ResponseEntity
                .ok()
                .body(service.getAll());
    }
}
