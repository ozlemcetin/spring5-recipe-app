package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeToRecipeCommandTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;
    RecipeToRecipeCommand objToCommand;

    @BeforeEach
    public void setUp() throws Exception {
        objToCommand = new RecipeToRecipeCommand(new CategoryToCategoryCommand(), new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()), new NotesToNotesCommand());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(objToCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(objToCommand.convert(new Recipe()));
    }

    @Test
    public void convert() throws Exception {

        //given
        Recipe recipe = new Recipe();
        {
            recipe.setId(RECIPE_ID);
            recipe.setDescription(DESCRIPTION);
            recipe.setDifficulty(DIFFICULTY);
            recipe.setPrepTime(PREP_TIME);
            recipe.setCookTime(COOK_TIME);
            recipe.setServings(SERVINGS);
            recipe.setSource(SOURCE);
            recipe.setUrl(URL);
            recipe.setDirections(DIRECTIONS);

            Notes notes = new Notes();
            {
                notes.setId(NOTES_ID);
                notes.setRecipeNotes(null);
            }
            recipe.setNotes(notes);

            Ingredient ingredient = new Ingredient();
            {
                ingredient.setId(INGRED_ID_1);
                ingredient.setDescription(null);
                ingredient.setAmount(null);
                ingredient.setUnitOfMeasure(null);
            }
            recipe.getIngredients().add(ingredient);

            Ingredient ingredient2 = new Ingredient();
            {
                ingredient2.setId(INGRED_ID_2);
            }
            recipe.getIngredients().add(ingredient2);

            Category category = new Category();
            {
                category.setId(CAT_ID_1);
                category.setDescription(null);
            }
            recipe.getCategories().add(category);

            Category category2 = new Category();
            {
                category2.setId(CAT_ID2);
            }
            recipe.getCategories().add(category2);

        }


        //when
        RecipeCommand command = objToCommand.convert(recipe);

        //then
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(DIFFICULTY, command.getDifficulty());

        assertNotNull(command.getNotesCommand());
        assertEquals(NOTES_ID, command.getNotesCommand().getId());

        assertNotNull(command.getIngredientCommands());
        assertEquals(2, command.getIngredientCommands().size());

        assertNotNull(command.getCategoryCommands());
        assertEquals(2, command.getCategoryCommands().size());

    }

}