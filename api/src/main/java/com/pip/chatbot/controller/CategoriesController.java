package com.pip.chatbot.controller;

import com.pip.chatbot.model.Category;
import com.pip.chatbot.model.Joke;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/jokes/categories")
@AllArgsConstructor
public class CategoriesController {
    private final CategoriesService categoriesService;

    @GetMapping("/{category}")
    public ResponseEntity<List<Joke>> getJokesForCategory(@PathVariable String category) {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(categoriesService.getJokesForCategory(category));
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(categoriesService.getAll());
    }

    @PostMapping("")
    public ResponseEntity<Category> create(@RequestBody Category body) {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(categoriesService.create(body));
    }

    @PutMapping("/{category}")
    public ResponseEntity<Category> update(@PathVariable String category, @RequestBody Category body) {
        return ResponseEntity
                .status(ResponseStatus.OK)
                .body(categoriesService.update(category, body));
    }

    @DeleteMapping("/{category}")
    public ResponseEntity<?> delete(@PathVariable String category) {
        categoriesService.delete(category);
        return Response.SUCCESS;
    }
}