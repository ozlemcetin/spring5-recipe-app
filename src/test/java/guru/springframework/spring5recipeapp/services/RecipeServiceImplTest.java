package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.never;

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
}