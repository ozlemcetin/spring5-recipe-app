package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeCommandToRecipeTest {
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

    RecipeCommandToRecipe commandToObj;


    @BeforeEach
    public void setUp() throws Exception {
        commandToObj = new RecipeCommandToRecipe(new CategoryCommandToCategory(), new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), new NotesCommandToNotes());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(commandToObj.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(commandToObj.convert(new RecipeCommand()));
    }

    @Test
    public void convert() throws Exception {

        //given
        RecipeCommand command = new RecipeCommand();
        {
            command.setId(RECIPE_ID);
            command.setDescription(DESCRIPTION);
            command.setPrepTime(PREP_TIME);
            command.setCookTime(COOK_TIME);
            command.setServings(SERVINGS);
            command.setSource(SOURCE);
            command.setUrl(URL);
            command.setDirections(DIRECTIONS);
            command.setDifficulty(DIFFICULTY);

            NotesCommand notesCommand = new NotesCommand();
            {
                notesCommand.setId(NOTES_ID);
                notesCommand.setRecipeNotes(null);
            }
            command.setNotesCommand(notesCommand);

            IngredientCommand ingredientCommand = new IngredientCommand();
            {
                ingredientCommand.setId(INGRED_ID_1);
                ingredientCommand.setDescription(null);
                ingredientCommand.setAmount(null);
                ingredientCommand.setUnitOfMeasureCommand(null);
            }
            command.getIngredientCommands().add(ingredientCommand);


            IngredientCommand ingredientCommand2 = new IngredientCommand();
            {
                ingredientCommand2.setId(INGRED_ID_2);
            }
            command.getIngredientCommands().add(ingredientCommand2);

            CategoryCommand categoryCommand = new CategoryCommand();
            {
                categoryCommand.setId(CAT_ID_1);
                categoryCommand.setDescription(null);
            }
            command.getCategoryCommands().add(categoryCommand);

            CategoryCommand categoryCommand2 = new CategoryCommand();
            {
                categoryCommand2.setId(CAT_ID2);
            }
            command.getCategoryCommands().add(categoryCommand2);
        }


        //when
        Recipe obj = commandToObj.convert(command);

        assertNotNull(obj);
        assertEquals(RECIPE_ID, obj.getId());
        assertEquals(DESCRIPTION, obj.getDescription());
        assertEquals(PREP_TIME, obj.getPrepTime());
        assertEquals(COOK_TIME, obj.getCookTime());
        assertEquals(SERVINGS, obj.getServings());
        assertEquals(SOURCE, obj.getSource());
        assertEquals(URL, obj.getUrl());
        assertEquals(DIRECTIONS, obj.getDirections());
        assertEquals(DIFFICULTY, obj.getDifficulty());

        assertNotNull(obj.getNotes());
        assertEquals(NOTES_ID, obj.getNotes().getId());

        assertNotNull(obj.getIngredients());
        assertEquals(2, obj.getIngredients().size());

        assertNotNull(obj.getCategories());
        assertEquals(2, obj.getCategories().size());

    }

}