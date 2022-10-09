package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceIT {


    public static final String NEW_DESCRIPTION = "New Description";
    @Autowired
    RecipeToRecipeCommand recipeToCommand;
    @Autowired
    RecipeCommandToRecipe commandToRecipe;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeService recipeService;

    @Transactional
    @Test
    void testSaveDescription() {

        //given
        RecipeCommand testRecipeCommand = null;
        {
            Iterable<Recipe> recipes = recipeRepository.findAll();
            Recipe testRecipe = recipes.iterator().next();
            testRecipeCommand = recipeToCommand.convert(testRecipe);
        }


        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipeCommand.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipeCommand.getCategoryCommands().size(), savedRecipeCommand.getCategoryCommands().size());
        assertEquals(testRecipeCommand.getIngredientCommands().size(), savedRecipeCommand.getIngredientCommands().size());


    }
}
