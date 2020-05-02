package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IngredientServiceImplTest {

    IngredientService ingredientService;

    @Mock
    RecipeService recipeService;

    IngredientToIngredientCommand toIngredientCommand;

    /*
    Constructor
     */
    public IngredientServiceImplTest() {

        //init converters
        this.toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeService, toIngredientCommand);
    }

    @Test
    void findCommandByRecipeIdIngredientId() {


        Long recipeId = 1L;
        Long ingredientId = 1L;
        String ingredientDesc = "Ingredient Description";
        {
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);

            {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(ingredientId);
                ingredient.setDescription(ingredientDesc);
                recipe.addIngredient(ingredient);
            }

            when(recipeService.findById(anyLong())).thenReturn(recipe);
        }

        //Service
        IngredientCommand ingredientCommand =
                ingredientService.findCommandByRecipeIdIngredientId(recipeId, ingredientId);

        assertNotNull(ingredientCommand);
        assertEquals(ingredientId, ingredientCommand.getId());
        assertEquals(ingredientDesc, ingredientCommand.getDescription());
        assertEquals(recipeId, ingredientCommand.getRecipeId());

        //verify
        {
            verify(recipeService).findById(anyLong());
        }
    }


}