package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jt on 6/21/17.
 */
public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = Long.valueOf(1L);

    UnitOfMeasureToUnitOfMeasureCommand objToCommand;

    @BeforeEach
    public void setUp() throws Exception {
        objToCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(objToCommand.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(objToCommand.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() throws Exception {

        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        {
            uom.setId(LONG_VALUE);
            uom.setDescription(DESCRIPTION);
        }

        //when
        UnitOfMeasureCommand command = objToCommand.convert(uom);

        //then
        assertNotNull(command);
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }

}