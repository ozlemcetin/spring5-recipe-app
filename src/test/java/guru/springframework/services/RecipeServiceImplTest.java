package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
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
    private RecipeToRecipeCommand recipeToCommand;
    @Mock
    private RecipeCommandToRecipe commandToRecipe;
    @Mock
    private RecipeRepository recipeRepository;

    private RecipeService recipeService;

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

            //when
            Mockito.when(recipeRepository.findAll()).thenReturn(set);
        }

        //then
        Set<Recipe> recipes = recipeService.getRecipes();
        Assertions.assertEquals(recipes.size(), set.size());

        //verify
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

            //when
            Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(recipeOptional);
        }

        //then
        Recipe recipeReturned = recipeService.findById(id);
        Assertions.assertNotNull(recipeReturned);
        Assertions.assertEquals(id, recipeReturned.getId());

        //verify
        {
            //findById() method in the RecipeRepository must be called only once
            Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
            Mockito.verify(recipeRepository, never()).findAll();
        }
    }

    @Test
    void getRecipeById_NotFound() {

        //given
        Long id = 1L;
        {
            //when
            Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        }

        //then
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            recipeService.findById(id);
        });

        Assertions.assertEquals("Recipe Not Found!", exception.getMessage());

        //verify
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

            //when
            when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        }
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(id);

            //when
            when(recipeToCommand.convert(any())).thenReturn(recipeCommand);
        }

        //then
        RecipeCommand command = recipeService.findCommandById(1L);
        AssertionErrors.assertNotNull("Null recipe command returned", command);
        Assertions.assertEquals(id, command.getId());


        //verify
        {
            Mockito.verify(recipeRepository, Mockito.times(1)).findById(anyLong());
            Mockito.verify(recipeToCommand, Mockito.times(1)).convert(any());
        }
    }

    @Test
    public void testDeleteById() throws Exception {

        //given
        Long id = 1L;
        {
            //no 'when', since method has void return type
        }

        //then
        recipeService.deleteById(id);


        //verify
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(anyLong());
    }
}