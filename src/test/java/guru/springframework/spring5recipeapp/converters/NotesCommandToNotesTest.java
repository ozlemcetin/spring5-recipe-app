package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotesCommandToNotesTest {

    public static final Long ID_VALUE = Long.valueOf(1L);
    public static final String RECIPE_NOTES = "Notes";
    NotesCommandToNotes commandToObj;

    @BeforeEach
    public void setUp() throws Exception {
        commandToObj = new NotesCommandToNotes();

    }

    @Test
    public void testNullParameter() throws Exception {
        assertNull(commandToObj.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(commandToObj.convert(new NotesCommand()));
    }

    @Test
    public void convert() throws Exception {

        //given
        NotesCommand notesCommand = new NotesCommand();
        {
            notesCommand.setId(ID_VALUE);
            notesCommand.setRecipeNotes(RECIPE_NOTES);
        }


        //when
        Notes obj = commandToObj.convert(notesCommand);

        //then
        assertNotNull(obj);
        assertEquals(ID_VALUE, obj.getId());
        assertEquals(RECIPE_NOTES, obj.getRecipeNotes());
    }

}