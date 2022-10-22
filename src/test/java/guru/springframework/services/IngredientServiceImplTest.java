package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


class IngredientServiceImplTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;
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
        Long recipeId = 1L;
        String newDescription = "newDescription";
        BigDecimal newAmount = new BigDecimal(12);
        Long unitOfMeasureId = 3L;
        IngredientCommand command = null;
        {
            command = new IngredientCommand();
            command.setRecipeId(recipeId);
            //command.setId(ingredientId);
            command.setDescription(newDescription);
            command.setAmount(newAmount);

            //setUnitOfMeasureCommand
            {
                UnitOfMeasureCommand unit = new UnitOfMeasureCommand();
                unit.setId(unitOfMeasureId);
                command.setUnitOfMeasureCommand(unit);
            }
        }

        {
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);

            //when
            when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        }

        UnitOfMeasure unitOfMeasure = null;
        {
            unitOfMeasure = new UnitOfMeasure();
            unitOfMeasure.setId(unitOfMeasureId);

            //when
            when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(unitOfMeasure));
        }

        Long ingredientId = 5L;
        {
            Recipe savedRecipe = new Recipe();
            savedRecipe.setId(recipeId);

            //addIngredient
            {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(ingredientId);
                ingredient.setDescription(newDescription);
                ingredient.setAmount(newAmount);
                ingredient.setUnitOfMeasure(unitOfMeasure);

                savedRecipe.addIngredient(ingredient);
            }

            //when
            when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);
        }


        //then
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        assertNotNull(savedCommand);
        assertEquals(ingredientId, savedCommand.getId());
        assertEquals(recipeId, savedCommand.getRecipeId());
        assertEquals(unitOfMeasureId, savedCommand.getUnitOfMeasureCommand().getId());

        //verify
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(anyLong());
        Mockito.verify(recipeRepository, Mockito.times(1)).save(any(Recipe.class));
    }


    @Test
    public void testDeleteById() throws Exception {

        //given
        Long recipeId = 1L;
        Long ingredientId = 5L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);

            //addIngredient
            {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(ingredientId);
                ingredient.setRecipe(recipe);

                recipe.addIngredient(ingredient);
            }

            //when
            when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        }

        //then
        ingredientService.deleteById(recipeId, ingredientId);

        //verify
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}