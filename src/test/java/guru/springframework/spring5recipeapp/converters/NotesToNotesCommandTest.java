package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotesToNotesCommandTest {

    NotesToNotesCommand toNotesCommand;

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Recipe Notes";

    @Before
    public void setUp() throws Exception {
        toNotesCommand = new NotesToNotesCommand();
    }

    @Test
    public void convert_null() {
        assertNull(toNotesCommand.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(toNotesCommand.convert(new Notes()));
    }


    @Test
    public void convert() {

        NotesCommand notesCommand = null;
        {
            Notes notes = new Notes();
            notes.setId(ID_VALUE);
            notes.setRecipeNotes(RECIPE_NOTES);

            notesCommand = toNotesCommand.convert(notes);
        }


        assertNotNull(notesCommand);
        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }
}