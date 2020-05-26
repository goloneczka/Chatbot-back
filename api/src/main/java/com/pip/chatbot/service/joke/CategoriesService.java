package com.pip.chatbot.service;

import com.pip.chatbot.exception.messages.CategoriesErrorMessages;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.repository.joke.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public List<Category> getAll() {
        return categoriesRepository.getAll();
    }

    public List<Joke> getJokesForCategory(String category) {
        return categoriesRepository.getJokesForCategory(category);
    }

    public Category create(Category category) {
        return categoriesRepository.create(category);
    }

    public Category update(String category, Category body) {
        return categoriesRepository.update(category, body.getCategory()).get();
    }

    public void delete(String category) {
        if (!categoriesRepository.delete(category)) {
            throw new ChatbotExceptionBuilder().addError(CategoriesErrorMessages.DELETE_FAILURE).build();
        }
    }
}
