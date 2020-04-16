package com.pip.chatbot.service;


import com.pip.chatbot.dao.JokeRepository;
import com.pip.chatbot.model.Category;
import com.pip.chatbot.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class JokeService {

    private JokeRepository jokeRepository;

    @Autowired
    public JokeService(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    public Optional<List<Joke>> getAllJoke() {
        return jokeRepository.getAllJoke();
    }

    public Optional<List<Category>> getAllCategory() {
        return jokeRepository.getAllCategory();
    }

    public Optional<Joke> getRandomJoke() {
        return jokeRepository.getById(new Random().nextInt(jokeRepository.getJokesTableSize()) + 1);
    }

    public Optional<Joke> getRandomJokeByCategory(String category) {
      return jokeRepository.getRandomOneByCategory(category);
    }

}
