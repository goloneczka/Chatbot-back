package com.pip.chatbot.controller;

import com.pip.chatbot.model.Joke;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.JokesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class JokesController {
    @Autowired
    private JokesService jokesService;

    @GetMapping("/jokes/{id}")
    public ResponseEntity<Joke> getJoke(@PathVariable int id) {
        return ResponseEntity.status(ResponseStatus.OK).body(jokesService.get(id));
    }

    @GetMapping("/jokes")
    public ResponseEntity<List<Joke>> getJokeList() {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(jokesService.getAll());
    }

    @PostMapping("/jokes")
    public ResponseEntity<Joke> postJoke(@RequestBody Joke body) {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(jokesService.create(body));
    }

    @PutMapping("/jokes/{id}")
    public ResponseEntity<Joke> updateJoke(@PathVariable int id, @RequestBody Joke body) {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(jokesService.update(id, body));
    }

    @DeleteMapping("/jokes/{id}")
    public ResponseEntity<?> deleteJoke(@PathVariable int id) {
        jokesService.delete(id);
        return Response.SUCCESS;
    }
}