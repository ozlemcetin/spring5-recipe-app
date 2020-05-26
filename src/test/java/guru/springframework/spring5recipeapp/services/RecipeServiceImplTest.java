package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {


    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe toRecipe;

    @Mock
    RecipeToRecipeCommand toRecipeCommand;


    @Before
    public void setUp() throws Exception {

        /*
        Give me a mock RecipeRepository
         */
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, toRecipe, toRecipeCommand);

    }

    @Test
    public void getRecipes() {

        //Given
        {
            Set<Recipe> recipeSet = new HashSet<>();
            recipeSet.add(new Recipe());

            /*
            We're saying Mockito, when recipeRepository.findAll() is called return the given data
             */
            when(recipeRepository.findAll()).thenReturn(recipeSet);
        }

        //Service
        Set<Recipe> recipeSet = recipeService.getRecipes();

        //Then
        int expectedSizeInt = 1;
        assertEquals(expectedSizeInt, recipeSet.size());

        //Verification Steps
        {
            /*
            Verify that recipeRepository findAll() is called once and only once
             */
            verify(recipeRepository, times(1)).findAll();
        }
    }

    @Test
    public void findById() {

        //Given
        Long id = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(id);
            when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        }

        //Service
        Recipe recipe = recipeService.findById(id);
        assertNotNull(recipe);
        assertEquals(id, recipe.getId());

        //Verify
        {
            verify(recipeRepository, times(1)).findById(anyLong());
            verify(recipeRepository, never()).findAll();
        }
    }

    @Test(expected = NotFoundException.class)
    public void showByIdNotFound() throws Exception {

        //Given
        Long recipeId = 1L;
        {
            Optional<Recipe> optionalRecipe = Optional.empty();

            //Causes Runtime Exception
            when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        }

        //Service
        Recipe recipe = recipeService.findById(recipeId);

        //Verify
        {
            verify(recipeRepository).findById(anyLong());
        }

    }

    @Test
    public void deleteById() {

        //no given since method has a void return type
        Long id = 1L;

        //when service
        recipeService.deleteById(id);

        //verify
        {
            verify(recipeRepository, times(1)).deleteById(anyLong());
        }

    }

}