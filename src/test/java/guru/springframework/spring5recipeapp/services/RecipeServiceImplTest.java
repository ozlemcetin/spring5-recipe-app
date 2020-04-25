package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    RecipeServiceImpl recipeServiceImpl;

    @Before
    public void setUp() throws Exception {
        /*
        Give me a mock RecipeRepository
         */
        MockitoAnnotations.initMocks(this);
        recipeServiceImpl = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes() {

        {
            Set<Recipe> recipeSet = new HashSet<>();
            recipeSet.add(new Recipe());

            /*
            We're saying Mockito, when recipeRepository.findAll() is called return the given data
             */
            when(recipeRepository.findAll()).thenReturn(recipeSet);
        }

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
}