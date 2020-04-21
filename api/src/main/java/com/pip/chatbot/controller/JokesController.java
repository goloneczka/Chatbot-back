package com.pip.chatbot.controller;



import com.pip.chatbot.model.joke.*;
import com.pip.chatbot.service.joke.JokesService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jokes")
public class JokesController {

    private final JokesService jokesService;

    @Autowired
    public JokesController(JokesService jokesService) {
        this.jokesService = jokesService;

    }

    @GetMapping(value = "/categories" )
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity
                .ok()
                .body(jokesService.getAllCategory());
    }

    @GetMapping(value = "/random" )
    public ResponseEntity<Joke> getRandomJoke(){
        return ResponseEntity
                .ok()
                .body(jokesService.getRandomJoke());
    }

    @GetMapping(value = "/random/{category}" )
    public ResponseEntity<Joke> getRandomJokeByCategory(@PathVariable String category){
        return ResponseEntity
                .ok()
                .body(jokesService.getRandomJokeByCategory(category));
    }

    @PostMapping()
    public ResponseEntity<Joke> createJoke(@RequestBody JokeApi jokeApi, @Qualifier("Joke") ModelMapper modelMapper) {
        Joke joke = modelMapper.map(jokeApi, new TypeToken<Joke>() {}.getType());
        return ResponseEntity
                .ok()
                .body(jokesService.createJoke(joke));
    }

    @PostMapping("/rate")
    public ResponseEntity<Mark> rateJoke(@RequestBody MarkApi markApi, @Qualifier("Joke") ModelMapper modelMapper) {
        Mark mark = modelMapper.map(markApi, new TypeToken<Mark>() {}.getType());
        return ResponseEntity
                .ok()
                .body(jokesService.rateJoke(mark));
    }


}
