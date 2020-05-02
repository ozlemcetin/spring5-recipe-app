package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe toRecipe;

    @Autowired
    RecipeToRecipeCommand toRecipeCommand;


    @Before
    public void setUp() throws Exception {

    }

    @Test
    @Transactional
    public void findCommandById() throws Exception {

        Long id = 1L;
        Recipe recipe = recipeService.findById(id);
        RecipeCommand recipeCommand = recipeService.findCommandById(id);

        assertNotNull(recipe);
        assertNotNull(recipeCommand);

        assertEquals(recipe.getId(), recipeCommand.getId());
        assertEquals(recipe.getSource(), recipeCommand.getSource());
        assertEquals(recipe.getCategories().size(), recipeCommand.getCategoryCommands().size());
        assertEquals(recipe.getIngredients().size(), recipeCommand.getIngredientCommands().size());

    }


    /*
      @Transactional is used
      as we are working with these entities outside the spring,
      outside the transactional context.
      We are accessing lazily loaded property
      so we have to have a Transactional to keep that inside of the context
     */
    @Test
    @Transactional
    public void saveRecipeCommand() throws Exception {

        String desc = "New Description";
        Recipe recipe = null;
        RecipeCommand savedRecipeCommand = null;
        {
            //Repository
            Iterable<Recipe> recipeIterable =
                    recipeRepository.findAll();

            recipe = recipeIterable.iterator().next();

            RecipeCommand recipeCommand = toRecipeCommand.convert(recipe);
            recipeCommand.setDescription(desc);

            //Service
            savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        }

        assertNotNull(recipe);
        assertNotNull(savedRecipeCommand);

        assertEquals(desc, savedRecipeCommand.getDescription());
        assertEquals(recipe.getId(), savedRecipeCommand.getId());
        assertEquals(recipe.getSource(), savedRecipeCommand.getSource());
        assertEquals(recipe.getCategories().size(), savedRecipeCommand.getCategoryCommands().size());
        assertEquals(recipe.getIngredients().size(), savedRecipeCommand.getIngredientCommands().size());

    }

}