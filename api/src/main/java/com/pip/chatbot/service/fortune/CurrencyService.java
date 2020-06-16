package com.pip.chatbot.service.fortune;

import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.StockErrorMessages;
import com.pip.chatbot.repository.fortune.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public String create(String currency) {
        currencyRepository.create(currency);
        return currency;
    }

    public List<String> getAll() {
        return currencyRepository.getAll();
    }

    public void delete(String id) {
        if (!currencyRepository.delete(id)) {
            throw new ChatbotExceptionBuilder().addError(StockErrorMessages.CURRENCY_NOT_FOUND).build();
        }
    }
}
