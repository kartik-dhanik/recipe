package com.demo_project.demo_project_app.service;

import com.demo_project.demo_project_app.model.Recipe;
import com.demo_project.demo_project_app.model.RecipeResponses;
import com.demo_project.demo_project_app.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        testRecipe = new Recipe();
        testRecipe.setId(1L);
        testRecipe.setName("Spaghetti");
        testRecipe.setCuisine("Italian");
        testRecipe.setInstructions("Cook pasta and add sauce");
    }

    @Test
    void loadRecipesFromExternalAPI_Success() {
        RecipeResponses recipesResponse = new RecipeResponses();
        recipesResponse.setRecipes(Arrays.asList(testRecipe));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/recipes")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(RecipeResponses.class)).thenReturn(Mono.just(recipesResponse));

        recipeService.loadRecipesFromExternalAPI();
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void searchRecipes_ReturnsFilteredRecipes() {
        String searchTerm = "Spaghetti";
        List<Recipe> expectedRecipes = Collections.singletonList(testRecipe);
        when(recipeRepository.searchRecipes(searchTerm)).thenReturn(expectedRecipes);

        List<Recipe> actualRecipes = recipeService.searchRecipes(searchTerm);
        assertEquals(expectedRecipes, actualRecipes);
        verify(recipeRepository).searchRecipes(searchTerm);
    }

    @Test
    void findById_WithValidId_ReturnsRecipe() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(testRecipe));

        Optional<Recipe> result = recipeService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(testRecipe, result.get());
    }

    @Test
    void findById_WithInvalidId_ReturnsEmpty() {
        when(recipeRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Recipe> result = recipeService.findById(999L);
        assertFalse(result.isPresent());
    }
}