package com.pip.chatbot.service;

import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;

import com.pip.chatbot.dao.JokeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoAuthService {

    private JokeDao jokeDao;

    @Autowired
    public NoAuthService(JokeDao jokeDao) {
        this.jokeDao = jokeDao;
    }

    public JokeRecord getAll(){
        return jokeDao.getAll();
    }
}
