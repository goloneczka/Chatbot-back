package com.pip.chatbot.service;

import com.pip.chatbot.model.Joke;
import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.JokesErrorMessages;
import com.pip.chatbot.repository.AdminJokesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminJokesService {
    private final AdminJokesRepository jokesRepository;

    public List<Joke> getAll() {
        return jokesRepository.getAll();
    }

    public Joke get(int id) {
        var result = jokesRepository.get(id);

        if (result.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(JokesErrorMessages.NOT_FOUND).build();
        }

        return result.get();
    }

    public Joke create(Joke joke) {
        return jokesRepository.create(joke);
    }

    public Joke update(Joke joke) {
        return jokesRepository.update(joke);
    }

    public void delete(int id) {
        if (!jokesRepository.delete(id)) {
            throw new ChatbotExceptionBuilder().addError(JokesErrorMessages.DELETE_FAILURE).build();
        }
    }
}
