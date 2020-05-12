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
    private final ModelMapper modelMapper;

    @Autowired
    public JokesController(JokesService jokesService, ModelMapper modelMapper) {
        this.jokesService = jokesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/categories" )
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity
                .ok()
                .body(jokesService.getAllCategories());
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
    public ResponseEntity<Joke> createJoke(@RequestBody JokeApi jokeApi) {
        Joke joke = modelMapper.map(jokeApi, Joke.class);
        return ResponseEntity
                .ok()
                .body(jokesService.createJoke(joke));
    }

    @PostMapping("/rate")
    public ResponseEntity<MarkApi> rateJoke(@RequestBody MarkApi markApi) {
        Mark mark = modelMapper.map(markApi, Mark.class);
        return ResponseEntity
                .ok()
                .body(jokesService.rateJoke(mark));
    }

    @GetMapping("/rate/{id}")
    public ResponseEntity<MarkApi> getJokeMark(@PathVariable String id) {
        MarkApi markApi = modelMapper.map(jokesService.getJokeMark(id), MarkApi.class);
        return ResponseEntity
                .ok()
                .body(markApi);
    }


}
