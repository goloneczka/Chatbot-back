package com.pip.chatbot.service;


import com.pip.chatbot.dao.JokeDao;
import com.pip.chatbot.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class JokeService {

    private JokeDao jokeDao;

    @Autowired
    public JokeService(JokeDao jokeDao) {
        this.jokeDao = jokeDao;
    }

    public List<Joke> getAll(){
        return jokeDao.getAll();
    }

    public Joke getRandomJoke() {
        return jokeDao.getRandomJoke(new Random().nextInt(jokeDao.getJokesTableSize()) + 1);
    }
}
