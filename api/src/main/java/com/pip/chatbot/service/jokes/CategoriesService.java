package com.pip.chatbot.service.jokes;

import com.pip.chatbot.exception.messages.jokes.CategoriesErrorMessages;
import com.pip.chatbot.model.jokes.Category;
import com.pip.chatbot.model.jokes.Joke;
import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.jokes.JokesErrorMessages;
import com.pip.chatbot.repository.jokes.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriesService {
    @Autowired
    private final CategoriesRepository categoriesRepository;

    public List<Category> getAll() {
        return categoriesRepository.getAll();
    }

    public List<Joke> getJokesForCategory(String category) {
        return categoriesRepository.getJokesForCategory(category);
    }

    public Category create(Category category) {
        var result = categoriesRepository.create(category);

        if (result.isEmpty()) {
            throw new ChatbotExceptionBuilder().addError(CategoriesErrorMessages.CREATE_FAILURE).build();
        }

        return result.get();
    }

    public void delete(String category) {
        if (!categoriesRepository.delete(category)) {
            throw new ChatbotExceptionBuilder().addError(CategoriesErrorMessages.DELETE_FAILURE).build();
        }
    }
}
