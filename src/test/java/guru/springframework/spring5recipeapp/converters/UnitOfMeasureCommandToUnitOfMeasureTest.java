package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure;

    public static final Long LONG_VALUE = 1L;
    public static final String DESCRIPTION = "Description";


    @Before
    public void setUp() throws Exception {
        toUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void convert_null() {
        assertNull(toUnitOfMeasure.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(toUnitOfMeasure.convert(new UnitOfMeasureCommand()));
    }


    @Test
    public void convert() {

        UnitOfMeasure uom = null;
        {
            UnitOfMeasureCommand command = new UnitOfMeasureCommand();
            command.setId(LONG_VALUE);
            command.setDescription(DESCRIPTION);

            uom = toUnitOfMeasure.convert(command);
        }

        //then
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }


}