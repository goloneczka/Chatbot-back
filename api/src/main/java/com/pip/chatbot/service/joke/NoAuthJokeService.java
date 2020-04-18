package com.pip.chatbot.service.joke;


import com.pip.chatbot.repository.joke.NoAuthJokeRepository;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class NoAuthJokeService {

    private NoAuthJokeRepository noAuthJokeRepository;

    public NoAuthJokeService(NoAuthJokeRepository noAuthJokeRepository) {
        this.noAuthJokeRepository = noAuthJokeRepository;
    }

    public List<Category> getAllConfirmedCategory() {
        return noAuthJokeRepository.getAllConfirmedCategory();
    }

    public Optional<Joke> getRandomJoke() {
        return noAuthJokeRepository.getRandomJoke();
    }

    public Optional<Joke> getRandomJokeByCategory(String category) {
        return noAuthJokeRepository.getRandomJokeByCategory(category);
    }

    public Optional<Joke> createJoke(Joke joke) {
        if (noAuthJokeRepository.getAllCategory().stream()
                .map(Category::getCategory)
                .anyMatch(joke.getCategory()::equals))
            return noAuthJokeRepository.addTemporaryJoke(joke);

        noAuthJokeRepository.addTemporaryCategory(joke.getCategory());
        return noAuthJokeRepository.addTemporaryJoke(joke);

    }
}
