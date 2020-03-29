package com.pip.chatbot.service;

import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;

import com.pip.chatbot.dao.JokeDao;
import com.pip.chatbot.model.Joke;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
