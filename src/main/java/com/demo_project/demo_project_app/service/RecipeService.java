package com.demo_project.demo_project_app.service;

import com.demo_project.demo_project_app.model.Recipe;
import com.demo_project.demo_project_app.model.RecipeResponses;
import com.demo_project.demo_project_app.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final WebClient webClient;

    public void loadRecipesFromExternalAPI() {
        log.info("Starting to load recipes from external API");
        Objects.requireNonNull(webClient.get()
                        .uri("/recipes")
                        .retrieve()
                        .bodyToMono(RecipeResponses.class)
                        .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                        .block())
                .getRecipes()
                .forEach(recipe -> {
                    log.info("Saving recipe: {}", recipe.getName());
                    recipeRepository.save(recipe);
                });
        log.info("Completed loading recipes from external API");
    }

    public List<Recipe> searchRecipes(String searchTerm) {
        log.info("Searching recipes with term: {}", searchTerm);
        return recipeRepository.searchRecipes(searchTerm);
    }

    public Optional<Recipe> findById(Long id) {
        log.info("Finding recipe by id: {}", id);
        return recipeRepository.findById(id);
    }
}