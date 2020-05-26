package com.pip.chatbot.repository.finance;

import com.pip.chatbot.model.finance.Symbol;

import java.util.ArrayList;
import java.util.List;

public class SymbolRepository {
    List<Symbol> symbols;

    public SymbolRepository() {
        symbols = new ArrayList<>();
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
