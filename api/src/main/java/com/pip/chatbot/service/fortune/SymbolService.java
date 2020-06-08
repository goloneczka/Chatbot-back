package com.pip.chatbot.service.fortune;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.CitiesErrorMessages;
import com.pip.chatbot.model.fortune.Symbol;
import com.pip.chatbot.repository.fortune.SymbolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SymbolService {
    private final SymbolRepository symbolRepository;

    public Symbol createSymbol(Symbol symbol){
        return symbolRepository.createSymbol(symbol);
    }

    public Symbol updateSymbol(Symbol symbol){
        return symbolRepository.updateSymbol(symbol)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(CitiesErrorMessages.NOT_FOUND).build());
    }

    public void deleteSymbol(String id){
        if (!symbolRepository.deleteSymbol(id)) {
            throw new ChatbotExceptionBuilder().addError(CitiesErrorMessages.NOT_FOUND).build();
        }
    }

    public List<Symbol> getAllCurrencies(){
        return symbolRepository.getAllCurrencies();
    }

    public List<Symbol> getAllCompanies(){
        return symbolRepository.getAllCompanies();
    }
}
