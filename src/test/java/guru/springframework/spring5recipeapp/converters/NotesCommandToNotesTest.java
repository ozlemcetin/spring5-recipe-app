package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotesCommandToNotesTest {

    NotesCommandToNotes commandToNotes;

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Recipe Notes";

    @Before
    public void setUp() throws Exception {
        commandToNotes = new NotesCommandToNotes();
    }


    @Test
    public void convert_null() {
        assertNull(commandToNotes.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(commandToNotes.convert(new NotesCommand()));
    }


    @Test
    public void convert() {

        Notes notes = null;
        {
            NotesCommand notesCommand = new NotesCommand();
            notesCommand.setId(ID_VALUE);
            notesCommand.setRecipeNotes(RECIPE_NOTES);

            notes = commandToNotes.convert(notesCommand);
        }


        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}