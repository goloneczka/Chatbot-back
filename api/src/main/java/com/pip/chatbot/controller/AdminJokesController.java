package com.pip.chatbot.controller;

import com.pip.chatbot.model.joke.Joke;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.joke.AdminJokesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/jokes")
@AllArgsConstructor
public class AdminJokesController {
    private final AdminJokesService adminJokesService;

    @GetMapping("/{id}")
    public ResponseEntity<Joke> get(@PathVariable int id) {
        return ResponseEntity.status(ResponseStatus.OK).body(adminJokesService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Joke>> getAll() {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(adminJokesService.getAll());
    }

    @PostMapping("")
    public ResponseEntity<Joke> create(@RequestBody Joke body) {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(adminJokesService.create(body));
    }

    @PutMapping("")
    public ResponseEntity<Joke> update(@RequestBody Joke body) {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(adminJokesService.update(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        adminJokesService.delete(id);
        return Response.SUCCESS;
    }
}