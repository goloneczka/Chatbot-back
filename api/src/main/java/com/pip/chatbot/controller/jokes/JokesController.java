package com.pip.chatbot.controller.jokes;

import com.pip.chatbot.model.jokes.Joke;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.jokes.JokesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/jokes")
public class JokesController {
    @Autowired
    private JokesService jokesService;

    @GetMapping("/{id}")
    public ResponseEntity<Joke> get(@PathVariable int id) {
        return ResponseEntity.status(ResponseStatus.OK).body(jokesService.get(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Joke>> getAll() {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(jokesService.getAll());
    }

    @PostMapping("/")
    public ResponseEntity<Joke> create(@RequestBody Joke body) {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(jokesService.create(body));
    }

    @PutMapping("/")
    public ResponseEntity<Joke> update(@RequestBody Joke body) {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(jokesService.update(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        jokesService.delete(id);
        return Response.SUCCESS;
    }
}