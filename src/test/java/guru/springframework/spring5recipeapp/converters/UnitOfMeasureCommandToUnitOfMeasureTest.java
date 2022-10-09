package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = Long.valueOf(1L);

    UnitOfMeasureCommandToUnitOfMeasure commandToObj;

    @BeforeEach
    public void setUp() throws Exception {
        commandToObj = new UnitOfMeasureCommandToUnitOfMeasure();

    }

    @Test
    public void testNullParamter() throws Exception {
        assertNull(commandToObj.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(commandToObj.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() throws Exception {

        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        {
            command.setId(LONG_VALUE);
            command.setDescription(DESCRIPTION);
        }

        //when
        UnitOfMeasure obj = commandToObj.convert(command);

        //then
        assertNotNull(obj);
        assertEquals(LONG_VALUE, obj.getId());
        assertEquals(DESCRIPTION, obj.getDescription());
    }

}