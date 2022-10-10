package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.AssertionErrors;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

class RecipeServiceImplTest {

    @Mock
    RecipeToRecipeCommand recipeToCommand;
    @Mock
    RecipeCommandToRecipe commandToRecipe;
    @Mock
    private RecipeRepository recipeRepository;

    private RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {

        //Give a mock RecipeRepository
        MockitoAnnotations.openMocks(this);

        //recipeService
        recipeService = new RecipeServiceImpl(recipeRepository, commandToRecipe, recipeToCommand);
    }


    @Test
    void getRecipes() throws Exception {

        //given
        Set<Recipe> set = null;
        {
            set = new HashSet<>();
            set.add(new Recipe());
            set.add(new Recipe());

            Mockito.when(recipeRepository.findAll()).thenReturn(set);
        }

        //when
        Set<Recipe> recipes = recipeService.getRecipes();
        Assertions.assertEquals(recipes.size(), set.size());

        //then
        {
            //findAll() method in the RecipeRepository must be called only once
            Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
        }
    }

    @Test
    void getRecipeById() throws Exception {

        //given
        Optional<Recipe> recipeOptional = null;
        Long id = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(id);
            recipeOptional = Optional.of(recipe);

            Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(recipeOptional);
        }

        //when
        Recipe recipeReturned = recipeService.findById(id);
        Assertions.assertNotNull(recipeReturned);
        Assertions.assertEquals(id, recipeReturned.getId());

        //then
        {
            //findById() method in the RecipeRepository must be called only once
            Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
            Mockito.verify(recipeRepository, never()).findAll();
        }
    }

    @Test
    void getRecipeById_NotFound() {

        //given
        Optional<Recipe> recipeOptional = null;
        Long id = 1L;
        {
            Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        }

        //when
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            recipeService.findById(id);
        });

        Assertions.assertEquals("Recipe Not Found!", exception.getMessage());

        //then
        {
            //findById() method in the RecipeRepository must be called only once
            Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        }
    }

    @Test
    void getRecipeCommandByIdTest() {

        //given
        Long id = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(1L);
            Optional<Recipe> recipeOptional = Optional.of(recipe);

            when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        }
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(id);

            when(recipeToCommand.convert(any())).thenReturn(recipeCommand);
        }

        //when
        RecipeCommand command = recipeService.findCommandById(1L);
        AssertionErrors.assertNotNull("Null recipe command returned", command);
        Assertions.assertEquals(id, command.getId());


        //then
        {
            Mockito.verify(recipeRepository, Mockito.times(1)).findById(anyLong());
            Mockito.verify(recipeToCommand, Mockito.times(1)).convert(any());
        }
    }

    @Test
    public void testDeleteById() throws Exception {

        //given
        Long id = 1L;

        //when
        recipeService.deleteById(id);

        //no 'when', since method has void return type

        //then
        {
            Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(anyLong());
        }

    }
}