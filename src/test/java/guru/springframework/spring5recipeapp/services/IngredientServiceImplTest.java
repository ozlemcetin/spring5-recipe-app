package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IngredientServiceImplTest {

    IngredientService ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientToIngredientCommand toIngredientCommand;
    IngredientCommandToIngredient toIngredient;


    /*
    Constructor
     */
    public IngredientServiceImplTest() {

        //init converters
        this.toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.toIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository, toIngredientCommand, toIngredient);
    }

    @Test
    void findCommandByRecipeIdIngredientId() {

        //Given
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

            when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        }

        //When Service
        IngredientCommand ingredientCommand =
                ingredientService.findCommandByRecipeIdIngredientId(recipeId, ingredientId);

        //Then
        assertNotNull(ingredientCommand);
        assertEquals(ingredientId, ingredientCommand.getId());
        assertEquals(ingredientDesc, ingredientCommand.getDescription());
        assertEquals(recipeId, ingredientCommand.getRecipeId());

        //Verify
        {
            verify(recipeRepository).findById(anyLong());
        }
    }

    @Test
    void saveIngredientCommand() {

        //Given
        Long recipeId = 1L;
        Long ingredientId = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);
            when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        }

        {
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);

            {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(ingredientId);
                recipe.addIngredient(ingredient);
            }

            when(recipeRepository.save(any())).thenReturn(recipe);
        }

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setId(ingredientId);

        //When Service
        IngredientCommand savedIngredientCommand
                = ingredientService.saveIngredientCommand(ingredientCommand);

        //Then
        assertNotNull(savedIngredientCommand);
        assertEquals(ingredientCommand.getId(), savedIngredientCommand.getId());
        assertEquals(ingredientCommand.getRecipeId(), ingredientCommand.getRecipeId());

        //Verify
        {
            verify(recipeRepository).findById(anyLong());
            verify(recipeRepository).save(any(Recipe.class));
        }

    }


}