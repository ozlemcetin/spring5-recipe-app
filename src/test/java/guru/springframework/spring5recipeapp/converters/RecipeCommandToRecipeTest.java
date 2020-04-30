package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Difficulty;
import guru.springframework.spring5recipeapp.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeCommandToRecipeTest {

    RecipeCommandToRecipe commandToRecipe;

    public static final Long RECIPE_ID = 1L;
    public static final String DESCRIPTION = "Description";
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer SERVINGS = Integer.valueOf("4");
    public static final String SOURCE = "Source";
    public static final String URL = "URL";
    public static final String DIRECTIONS = "Directions";

    public static final Long INGRED_ID_1 = 1L;
    public static final Long INGRED_ID_2 = 2L;

    public static final Difficulty DIFFICULTY = Difficulty.EASY;

    public static final Long NOTES_ID = 1L;

    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID_2 = 2L;


    @Before
    public void setUp() throws Exception {

        commandToRecipe = new RecipeCommandToRecipe(
                new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void convert_null() {
        assertNull(commandToRecipe.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(commandToRecipe.convert(new RecipeCommand()));
    }


    @Test
    public void convert() {

        Recipe recipe = null;
        {
            RecipeCommand recipeCommand = new RecipeCommand();

            recipeCommand.setId(RECIPE_ID);
            recipeCommand.setDescription(DESCRIPTION);
            recipeCommand.setPrepTime(PREP_TIME);
            recipeCommand.setCookTime(COOK_TIME);
            recipeCommand.setServings(SERVINGS);
            recipeCommand.setSource(SOURCE);
            recipeCommand.setUrl(URL);
            recipeCommand.setDirections(DIRECTIONS);

            {
                IngredientCommand ingredientCommand1 = new IngredientCommand();
                ingredientCommand1.setId(INGRED_ID_1);
                recipeCommand.getIngredientCommands().add(ingredientCommand1);

                IngredientCommand ingredientCommand2 = new IngredientCommand();
                ingredientCommand2.setId(INGRED_ID_2);
                recipeCommand.getIngredientCommands().add(ingredientCommand2);
            }


            recipeCommand.setDifficulty(DIFFICULTY);

            {
                NotesCommand notesCommand = new NotesCommand();
                notesCommand.setId(NOTES_ID);
                recipeCommand.setNotesCommand(notesCommand);
            }

            {
                CategoryCommand categoryCommand1 = new CategoryCommand();
                categoryCommand1.setId(CAT_ID_1);
                recipeCommand.getCategoryCommands().add(categoryCommand1);

                CategoryCommand categoryCommand2 = new CategoryCommand();
                categoryCommand2.setId(CAT_ID_2);
                recipeCommand.getCategoryCommands().add(categoryCommand2);

            }


            //when
            recipe = commandToRecipe.convert(recipeCommand);
        }

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());

        assertEquals(2, recipe.getIngredients().size());
        //assertEquals(INGRED_ID_1, recipe.getIngredients().iterator().next().getId());

        assertEquals(DIFFICULTY, recipe.getDifficulty());

        assertEquals(NOTES_ID, recipe.getNotes().getId());

        assertEquals(2, recipe.getCategories().size());

    }

}