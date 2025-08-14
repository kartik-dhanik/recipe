package com.demo_project.demo_project_app.controller;

import com.demo_project.demo_project_app.model.Recipe;
import com.demo_project.demo_project_app.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipe API", description = "Operations for managing recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/load")
    @Operation(summary = "Load recipes from external API")
    public ResponseEntity<Void> loadRecipes() {
        recipeService.loadRecipesFromExternalAPI();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search recipes by name or cuisine")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String searchTerm) {
        return ResponseEntity.ok(recipeService.searchRecipes(searchTerm));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find recipe by ID")
    public ResponseEntity<Recipe> findRecipe(@PathVariable Long id) {
        return recipeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}