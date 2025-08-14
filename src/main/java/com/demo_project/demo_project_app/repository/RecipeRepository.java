package com.demo_project.demo_project_app.repository;

import com.demo_project.demo_project_app.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r " +
            "WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(r.cuisine) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Recipe> searchRecipes(@Param("searchTerm") String searchTerm);
}
