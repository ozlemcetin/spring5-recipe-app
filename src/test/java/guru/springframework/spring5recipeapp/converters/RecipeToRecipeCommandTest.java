package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeToRecipeCommandTest {

    RecipeToRecipeCommand toRecipeCommand;

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
        toRecipeCommand = new RecipeToRecipeCommand(
                new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand());
    }

    @Test
    public void convert_null() {
        assertNull(toRecipeCommand.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(toRecipeCommand.convert(new Recipe()));
    }

    @Test
    public void convert() {

        RecipeCommand recipeCommand = null;
        {
            Recipe recipe = new Recipe();

            recipe.setId(RECIPE_ID);
            recipe.setDescription(DESCRIPTION);
            recipe.setPrepTime(PREP_TIME);
            recipe.setCookTime(COOK_TIME);
            recipe.setServings(SERVINGS);
            recipe.setSource(SOURCE);
            recipe.setUrl(URL);
            recipe.setDirections(DIRECTIONS);

            {
                Ingredient ingredient1 = new Ingredient();
                ingredient1.setId(INGRED_ID_1);
                recipe.getIngredients().add(ingredient1);

                Ingredient ingredient2 = new Ingredient();
                ingredient2.setId(INGRED_ID_2);
                recipe.getIngredients().add(ingredient2);
            }

            recipe.setDifficulty(DIFFICULTY);

            {
                Notes notes = new Notes();
                notes.setId(NOTES_ID);
                recipe.setNotes(notes);
            }

            {
                Category category1 = new Category();
                category1.setId(CAT_ID_1);
                recipe.getCategories().add(category1);

                Category category2 = new Category();
                category2.setId(CAT_ID_2);
                recipe.getCategories().add(category2);

            }


            //when
            recipeCommand = toRecipeCommand.convert(recipe);
        }


        assertNotNull(recipeCommand);
        assertEquals(RECIPE_ID, recipeCommand.getId());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());

        assertEquals(2, recipeCommand.getIngredientCommands().size());
        //assertEquals(INGRED_ID_1, recipeCommand.getIngredients().iterator().next().getId());

        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());

        assertEquals(NOTES_ID, recipeCommand.getNotesCommand().getId());

        assertEquals(2, recipeCommand.getCategoryCommands().size());

    }

}