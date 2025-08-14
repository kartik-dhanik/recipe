package com.demo_project.demo_project_app.model;

import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.List;

@Data
@Entity
@Indexed
public class Recipe {
    @Id
    private Long id;

    @FullTextField
    private String name;

    @FullTextField
    private String cuisine;

    @ElementCollection
    @Column(length = 2000)
    private List<String> instructions;

    @ElementCollection
    private List<String> ingredients;

    private String image;
    private Double rating;
    private Integer reviewCount;
    private Integer prepTimeMinutes;
    private Integer cookTimeMinutes;
    private Integer servings;
    private String difficulty;
}