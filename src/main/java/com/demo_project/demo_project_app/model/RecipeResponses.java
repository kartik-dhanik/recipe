package com.demo_project.demo_project_app.model;

import lombok.Data;

import java.util.List;

@Data
public class RecipeResponses {
    private List<Recipe> recipes;
    private Integer total;
    private Integer skip;
    private Integer limit;
}