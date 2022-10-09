package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jt on 6/21/17.
 */
public class NotesToNotesCommandTest {

    public static final Long ID_VALUE = Long.valueOf(1L);
    public static final String RECIPE_NOTES = "Notes";
    NotesToNotesCommand objToCommand;

    @BeforeEach
    public void setUp() throws Exception {
        objToCommand = new NotesToNotesCommand();
    }

    @Test
    public void testNullConvert() throws Exception {
        assertNull(objToCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(objToCommand.convert(new Notes()));
    }

    @Test
    public void convert() throws Exception {

        //given
        Notes obj = new Notes();
        {
            obj.setId(ID_VALUE);
            obj.setRecipeNotes(RECIPE_NOTES);
        }


        //when
        NotesCommand command = objToCommand.convert(obj);

        //then
        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(RECIPE_NOTES, command.getRecipeNotes());
    }


}