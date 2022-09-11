package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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

        //given
        Set<Recipe> set = null;
        {
            set = new HashSet<>();
            {
                Recipe recipe = new Recipe();
                recipe.setId(1L);
                set.add(recipe);
            }
            {
                Recipe recipe = new Recipe();
                recipe.setId(2L);
                set.add(recipe);
            }

            Mockito.when(recipeService.getRecipes()).thenReturn(set);
        }

        //when
        String viewName = controller.getIndexPage(model);
        Assertions.assertEquals("index", viewName);

        //then
        {
            ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

            Mockito.verify(model, Mockito.times(1))
                    //The fist value is equal to "recipes", the second value is going to be any set
                    //.addAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.anySet());
                    .addAttribute(ArgumentMatchers.eq("recipes"), argumentCaptor.capture());

            Set<Recipe> returnedSet = argumentCaptor.getValue();
            Assertions.assertEquals(set.size(), returnedSet.size());
        }
        {
            Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        }

    }
}