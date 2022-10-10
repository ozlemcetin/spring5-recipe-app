package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


class IngredientServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    private RecipeRepository recipeRepository;
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {

        //Give a mock RecipeRepository
        MockitoAnnotations.openMocks(this);

        //ingredientService
        ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository,

                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),

                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
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


    @Test
    public void testSaveRecipeCommand() throws Exception {

        //given
        IngredientCommand command = null;
        Long ingredientId = 3L;
        Long recipeId = 1L;
        {
            command = new IngredientCommand();
            command.setId(ingredientId);
            command.setRecipeId(recipeId);
        }
        {
            //when
            when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        }
        {
            Recipe savedRecipe = new Recipe();
            savedRecipe.setId(recipeId);

            Ingredient ingredient = new Ingredient();
            ingredient.setId(ingredientId);
            savedRecipe.addIngredient(ingredient);

            //when
            when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);
        }


        //then
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        assertNotNull(savedCommand);
        assertEquals(ingredientId, savedCommand.getId());
        assertEquals(recipeId, savedCommand.getRecipeId());

        //verify
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(anyLong());
        Mockito.verify(recipeRepository, Mockito.times(1)).save(any(Recipe.class));

    }
}