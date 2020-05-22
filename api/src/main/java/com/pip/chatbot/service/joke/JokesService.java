package com.pip.chatbot.service.joke;


import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.JokesErrorMessages;
import com.pip.chatbot.exception.messages.MarksErrorMessages;
import com.pip.chatbot.model.joke.Mark;
import com.pip.chatbot.model.joke.MarkApi;
import com.pip.chatbot.repository.joke.JokesRepository;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;

import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class JokesService {

    private final JokesRepository jokesRepository;

    public JokesService(JokesRepository jokesRepository) {
        this.jokesRepository = jokesRepository;
    }

    private boolean doesCategoryExists(String category){
        return jokesRepository.getAllCategories().stream()
                .map(Category::getCategory)
                .anyMatch(category::equals);
    }

    public List<Category> getAllCategories() {
        return jokesRepository.getAllConfirmedCategories();
    }

    public Joke getRandomJoke() {
        return jokesRepository.getRandomJoke()
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(JokesErrorMessages.NOT_FOUND).build());
    }

    public Joke getRandomJokeByCategory(String category) {
        return jokesRepository.getRandomJokeByCategory(category)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(JokesErrorMessages.NOT_FOUND).build());
    }

    public Joke createJoke(Joke joke) {
        if (doesCategoryExists(joke.getCategory()))
            return jokesRepository.createTemporaryJoke(joke)
                    .orElseThrow(() -> new ChatbotExceptionBuilder().addError(JokesErrorMessages.CREATE_FAILURE).build());

        jokesRepository.createTemporaryCategory(joke.getCategory());
        return jokesRepository.createTemporaryJoke(joke)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(JokesErrorMessages.CREATE_FAILURE).build());
    }

    public Mark rateJoke(Mark mark) {
        return jokesRepository.createMark(mark)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(MarksErrorMessages.CREATE_FAILURE).build());
    }

    public MarkApi getJokeMark(String id) {
        return jokesRepository.getAvgJokeMark(id)
                .orElseThrow(() -> new ChatbotExceptionBuilder().addError(MarksErrorMessages.NOT_FOUND).build());

    }
}
