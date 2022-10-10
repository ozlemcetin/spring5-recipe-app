package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


class IngredientServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {

        //Give a mock RecipeRepository
        MockitoAnnotations.openMocks(this);

        //ingredientService
        ingredientService = new IngredientServiceImpl(recipeRepository, new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
    }


    @Test
    void findByRecipeIdAndIngredientId() throws Exception {


        //given
        Long recipeId = 1L;
        Long ingredientId = 3L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);

            {
                Ingredient ingredient1 = new Ingredient();
                ingredient1.setId(1L);
                recipe.addIngredient(ingredient1);
            }
            {

                Ingredient ingredient2 = new Ingredient();
                ingredient2.setId(2L);
                recipe.addIngredient(ingredient2);
            }
            {

                Ingredient ingredient3 = new Ingredient();
                ingredient3.setId(ingredientId);
                recipe.addIngredient(ingredient3);
            }

            //When
            when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        }

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);
        assertEquals(ingredientId, ingredientCommand.getId());
        assertEquals(recipeId, ingredientCommand.getRecipeId());

        //verify
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(anyLong());
    }
}