package com.pip.chatbot.service.joke;

import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.JokesErrorMessages;
import com.pip.chatbot.repository.joke.AdminJokesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminJokesService {
    private final AdminJokesRepository adminJokesRepository;

    public List<Joke> getAll() {
        return adminJokesRepository.getAll();
    }

    public List<Joke> getAllUnconfirmedJokes() {
        return adminJokesRepository.getAllUnconfirmedJokes();
    }

    public Joke get(int id) {
        var result = adminJokesRepository.get(id);

        if (result.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(JokesErrorMessages.NOT_FOUND).build();
        }

        return result.get();
    }

    public Joke create(Joke joke) {
        return adminJokesRepository.create(joke);
    }

    public Joke update(Joke joke) {
        return adminJokesRepository.update(joke);
    }

    public void delete(int id) {
        if (!adminJokesRepository.delete(id)) {
            throw new ChatbotExceptionBuilder().addError(JokesErrorMessages.DELETE_FAILURE).build();
        }
    }

    public Joke confirm(int id) {
        return adminJokesRepository.confirmJoke(id);
    }
}
