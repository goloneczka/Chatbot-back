package com.pip.chatbot.service.fortune;

import com.pip.chatbot.model.finance.Symbol;
import com.pip.chatbot.repository.fortune.SymbolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SymbolService {
    private final SymbolRepository symbolRepository;

    public List<Symbol> getAllCurrencies(){
        return symbolRepository.getAllCurrencies();
    }

    public List<Symbol> getAllCompanies(){
        return symbolRepository.getAllCompanies();
    }
}
