package com.pip.chatbot.controller;


import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.ForecastsErrorMessages;
import com.pip.chatbot.exception.messages.JokesErrorMessages;
import com.pip.chatbot.model.forecast.Country;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.joke.NoAuthJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jokes")
public class NoAuthJokeController {

    private NoAuthJokeService noAuthJokeService;

    @Autowired
    public NoAuthJokeController(NoAuthJokeService noAuthJokeService) {
        this.noAuthJokeService = noAuthJokeService;
    }

    @GetMapping(value = "/categories" )
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity
                .ok()
                .body(noAuthJokeService.getAllCategory().orElseThrow(NullPointerException::new));
    }

    @GetMapping(value = "/random" )
    public ResponseEntity<Joke> getRandomJoke(){
        return ResponseEntity
                .ok()
                .body(noAuthJokeService.getRandomJoke()
                        .orElseThrow(() -> new ChatbotExceptionBuilder().addError(JokesErrorMessages.NOT_FOUND).build()));
    }

    @GetMapping(value = "/random/{category}" )
    public ResponseEntity<Joke> getRandomJokeByCategory(@PathVariable String category){
        return ResponseEntity
                .ok()
                .body(noAuthJokeService.getRandomJokeByCategory(category)
                        .orElseThrow(() -> new ChatbotExceptionBuilder().addError(JokesErrorMessages.NOT_FOUND).build()));
    }

    @PostMapping("/{id}/{mark}")
    public ResponseEntity<Joke> createJoke(@PathVariable String id, @PathVariable Double mark) {
        return ResponseEntity
                .ok()
                .body(noAuthJokeService.getRandomJokeByCategory(category)
                        .orElseThrow(() -> new ChatbotExceptionBuilder().addError(JokesErrorMessages.CREATE_FAILURE).build()));
    }


}
