package com.pip.chatbot.service;

import com.pip.chatbot.model.Joke;
import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.JokesErrorMessages;
import com.pip.chatbot.repository.JokesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JokesService {
    @Autowired
    private final JokesRepository jokesRepository;

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
        var result = jokesRepository.create(joke);

        if (result.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(JokesErrorMessages.CREATE_FAILURE).build();
        }

        return result.get();
    }

    public Joke update(int id, Joke joke) {
        var result = jokesRepository.update(id, joke);

        if (result.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(JokesErrorMessages.UPDATE_FAILURE).build();
        }

        return result.get();
    }

    public void delete(int id) {
        if (!jokesRepository.delete(id)) {
            throw new ChatbotExceptionBuilder().addError(JokesErrorMessages.DELETE_FAILURE).build();
        }
    }
}
