package com.pip.chatbot.controller;

import com.pip.chatbot.dto.JokeDto;
import com.pip.chatbot.service.JokesService;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;

@RestController
public class JokesController {
    @Autowired
    JokesService jokesService;

    @GetMapping("/admin/jokes/{id}")
    @ResponseBody
    public ResponseEntity<?> getJoke(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(jokesService.getJoke(id));
        }

        catch (SQLException | IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new HashMap.SimpleEntry<>("message", "Joke not found"));
        }
    }

    @GetMapping("/admin/jokes")
    @ResponseBody
    public ResponseEntity<?> getJokeList() {
        try {
            return ResponseEntity
                    .ok()
                    .body(jokesService.getJokeList());
        }

        catch (SQLException e) {
            return ResponseEntity
                    .status(404)
                    .body(new HashMap.SimpleEntry<>("message", "Joke list not found"));
        }
    }

    @PostMapping("/admin/jokes")
    @ResponseBody
    public ResponseEntity<?> postJoke(@RequestBody JokeDto body) {
        try {
            return ResponseEntity
                    .ok()
                    .body(jokesService.postJoke(body));
        }

        catch (SQLException | DataAccessException e) {
            return ResponseEntity
                    .status(500)
                    .body(new HashMap.SimpleEntry<>("message", "Joke could not be posted"));
        }
    }

    @PostMapping("/admin/jokes/{id}")
    @ResponseBody
    public ResponseEntity<?> updateJoke(@PathVariable int id, @RequestBody JokeDto body) {
        try {
            return ResponseEntity
                    .ok()
                    .body(jokesService.updateJoke(id, body));
        }

        catch (SQLException e) {
            return ResponseEntity
                    .status(500)
                    .body(new HashMap.SimpleEntry<>("message", "Joke could not be updated"));
        }

        catch (IndexOutOfBoundsException e) {
            return ResponseEntity
                    .status(404)
                    .body(new HashMap.SimpleEntry<>("message", "Joke not found"));
        }
    }

    @DeleteMapping("/admin/jokes/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteJoke(@PathVariable int id) {
        try {
            boolean success = jokesService.deleteJoke(id);

            if (success) {
                return ResponseEntity
                        .ok()
                        .body(new HashMap.SimpleEntry<>("success", true));
            }

            return ResponseEntity
                    .status(500)
                    .body(new HashMap.SimpleEntry<>("message", "Joke not found"));
        }

        catch (SQLException e) {
            return ResponseEntity
                    .status(500)
                    .body(new HashMap.SimpleEntry<>("message", "Joke could not be deleted"));
        }
    }
}