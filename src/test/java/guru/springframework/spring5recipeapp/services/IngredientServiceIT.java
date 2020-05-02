package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class IngredientServiceIT {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientToIngredientCommand toIngredientCommand;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    void findCommandByRecipeIdIngredientId() {

        Long recipeId = 1L;
        Long ingredientId = 1L;

        //Service
        Recipe recipe = recipeService.findById(recipeId);
        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdIngredientId(recipeId, ingredientId);

        assertNotNull(recipe);
        assertNotNull(ingredientCommand);
        assertEquals(recipe.getId(), ingredientCommand.getRecipeId());

    }
}