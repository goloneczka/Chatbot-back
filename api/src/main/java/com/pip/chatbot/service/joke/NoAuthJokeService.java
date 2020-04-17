package com.pip.chatbot.service.joke;


import com.pip.chatbot.repository.joke.NoAuthJokeRepository;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class NoAuthJokeService {

    private NoAuthJokeRepository noAuthJokeRepository;

    public NoAuthJokeService(NoAuthJokeRepository noAuthJokeRepository) {
        this.noAuthJokeRepository = noAuthJokeRepository;
    }

    public Optional<List<Category>> getAllCategory() {
        return noAuthJokeRepository.getAllCategory();
    }

    public Optional<Joke> getRandomJoke() {
        return noAuthJokeRepository.getById(new Random().nextInt(noAuthJokeRepository.getJokesTableSize()) + 1);
    }

    public Optional<Joke> getRandomJokeByCategory(String category) {
      return noAuthJokeRepository.getRandomOneByCategory(category);
    }

}
