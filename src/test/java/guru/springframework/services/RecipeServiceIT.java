package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {


    private final String NEW_DESCRIPTION = "New Description";
    @Autowired
    private RecipeToRecipeCommand recipeToCommand;
    @Autowired
    private RecipeCommandToRecipe commandToRecipe;
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


        //then
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //assert
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipeCommand.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipeCommand.getCategoryCommands().size(), savedRecipeCommand.getCategoryCommands().size());
        assertEquals(testRecipeCommand.getIngredientCommands().size(), savedRecipeCommand.getIngredientCommands().size());

    }
}
