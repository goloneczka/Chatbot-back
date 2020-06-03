package com.pip.chatbot.repository.finance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.model.finance.Symbol;

import java.util.ArrayList;
import java.util.List;

public class SymbolRepository {
    List<Symbol> symbols;

    public SymbolRepository(){
        try{
            symbols = new ObjectMapper().readValue(getClass().getResourceAsStream("/finance/symbols.json"), new TypeReference<>() {});
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public List<Symbol> getAllCurrencies() {
        List<Symbol> resultSymbols = new ArrayList<>();
        for (Symbol symbol : symbols) {
            if (symbol.isCurrency())
                resultSymbols.add(symbol);
        }
        return resultSymbols;
    }

    public List<Symbol> getAllCompanies() {
        List<Symbol> resultSymbols = new ArrayList<>();
        for (Symbol symbol : symbols) {
            if (!symbol.isCurrency())
                resultSymbols.add(symbol);
        }
        return resultSymbols;
    }

    public Symbol create(Symbol symbol){
        symbols.add(symbol);
        return symbol;
    }
}
