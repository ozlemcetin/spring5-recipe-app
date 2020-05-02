package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
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


    RecipeServiceImpl recipeServiceImpl;

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
        recipeServiceImpl = new RecipeServiceImpl(recipeRepository, toRecipe, toRecipeCommand);

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
        int expectedSizeInt = 1;
        Set<Recipe> recipeSet = recipeServiceImpl.getRecipes();
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
        Recipe recipe = recipeServiceImpl.findById(id);
        assertNotNull(recipe);
        assertEquals(id, recipe.getId());

        //Verify
        {
            verify(recipeRepository, times(1)).findById(anyLong());
            verify(recipeRepository, never()).findAll();
        }
    }

    @Test
    public void deleteById() {

        //no when since method has a void return type

        //Service
        Long id = 1L;
        recipeServiceImpl.deleteById(id);

        //Verify
        {
            verify(recipeRepository, times(1)).deleteById(anyLong());
        }

    }

}