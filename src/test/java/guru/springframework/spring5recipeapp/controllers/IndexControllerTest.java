package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

class IndexControllerTest {

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;
    private IndexController controller;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        controller = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {

        Set<Recipe> set = null;
        {
            set = new HashSet<>();
            set.add(new Recipe());
            set.add(new Recipe());

            Mockito.when(recipeService.getRecipes()).thenReturn(set);
        }

        String viewName = controller.getIndexPage(model);
        Assertions.assertEquals("index", viewName);

        {
            Mockito.verify(model, Mockito.times(1))
                    //The fist value is equal to "recipes", the second value is going to be any set
                    .addAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.anySet());

            Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        }
    }
}