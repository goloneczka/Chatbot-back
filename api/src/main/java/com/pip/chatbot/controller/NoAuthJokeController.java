package com.pip.chatbot.controller;


import com.pip.chatbot.exception.ChatbotExceptionBuilder;
import com.pip.chatbot.exception.messages.JokesErrorMessages;
import com.pip.chatbot.exception.messages.MarksErrorMessages;
import com.pip.chatbot.model.joke.Category;
import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.model.joke.Mark;
import com.pip.chatbot.service.joke.MarkService;
import com.pip.chatbot.service.joke.NoAuthJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jokes")
public class NoAuthJokeController {

    private NoAuthJokeService noAuthJokeService;
    private MarkService markService;

    @Autowired
    public NoAuthJokeController(NoAuthJokeService noAuthJokeService, MarkService markService) {
        this.noAuthJokeService = noAuthJokeService;
        this.markService = markService;
    }

    @GetMapping(value = "/categories" )
    public ResponseEntity<List<Category>> getAllConfirmedCategory(){
        return ResponseEntity
                .ok()
                .body(noAuthJokeService.getAllConfirmedCategory());
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

    @PostMapping()
    public ResponseEntity<Joke> createJoke(@RequestBody Joke joke) {
        return ResponseEntity
                .ok()
                .body(noAuthJokeService.createJoke(joke)
                        .orElseThrow(() -> new ChatbotExceptionBuilder().addError(JokesErrorMessages.CREATE_FAILURE).build()));
    }

    @PostMapping("/rate")
    public ResponseEntity<Mark> rateJoke(@RequestBody Mark mark) {
        return ResponseEntity
                .ok()
                .body(markService.rateJoke(mark)
                        .orElseThrow(() -> new ChatbotExceptionBuilder().addError(MarksErrorMessages.CREATE_FAILURE).build()));
    }


}
