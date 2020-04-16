package com.pip.chatbot.controller;


import com.pip.chatbot.model.Category;
import com.pip.chatbot.model.Joke;
import com.pip.chatbot.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jokes")
public class NoAuthController {

    private JokeService jokeService;

    @Autowired
    public NoAuthController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping
    public ResponseEntity<List<Joke>> getAllJoke(){
        return ResponseEntity
                .ok()
                .body(jokeService.getAllJoke().orElseThrow(NullPointerException::new));
    }

    @GetMapping(value = "/categories" )
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity
                .ok()
                .body(jokeService.getAllCategory().orElseThrow(NullPointerException::new));
    }

    @GetMapping(value = "/random" )
    public ResponseEntity<Joke> getRandomJoke(){
        return ResponseEntity
                .ok()
                .body(jokeService.getRandomJoke().orElseThrow(NullPointerException::new));
    }

    @GetMapping(value = "/random/{category}" )
    public ResponseEntity<Joke> getRandomJokeByCategory(@PathVariable String category){
        return ResponseEntity
                .ok()
                .body(jokeService.getRandomJokeByCategory(category).orElseThrow(NullPointerException::new));
    }


}
