package com.pip.chatbot.controller;


import com.pip.chatbot.jooq.jokes.tables.records.JokeRecord;
import com.pip.chatbot.model.Joke;
import com.pip.chatbot.service.JokeService;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/jokes")
public class NoAuthController {

    private JokeService jokeService;

    @Autowired
    public NoAuthController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Joke> getAll(){
        return jokeService.getAll();
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET )
    public Joke getRandomJoke(){
        return jokeService.getRandomJoke();
    }
}
