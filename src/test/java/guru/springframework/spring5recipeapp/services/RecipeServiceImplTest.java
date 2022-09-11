package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;
    private RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {

        //Give a mock RecipeRepository
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {

        Set<Recipe> set = null;
        {
            set = new HashSet<>();
            set.add(new Recipe());
            set.add(new Recipe());

            Mockito.when(recipeRepository.findAll()).thenReturn(set);
        }

        Set<Recipe> recipes = recipeService.getRecipes();
        Assertions.assertEquals(recipes.size(), set.size());

        {
            //findAll() method in the RecipeRepository must be called only once
            Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
        }
    }
}