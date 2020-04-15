package com.pip.chatbot.controller.jokes;

import com.pip.chatbot.model.jokes.Category;
import com.pip.chatbot.model.jokes.Joke;
import com.pip.chatbot.payload.response.Response;
import com.pip.chatbot.payload.response.ResponseStatus;
import com.pip.chatbot.service.jokes.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/jokes/categories")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

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

    @DeleteMapping("/{category}")
    public ResponseEntity<?> delete(@PathVariable String category) {
        categoriesService.delete(category);
        return Response.SUCCESS;
    }
}