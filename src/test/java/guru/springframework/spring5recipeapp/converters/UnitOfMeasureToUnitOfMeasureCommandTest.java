package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    public static final Long LONG_VALUE = 1L;
    public static final String DESCRIPTION = "Description";

    @Before
    public void setUp() throws Exception {
        toUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }


    @Test
    public void convert_null() {
        assertNull(toUnitOfMeasureCommand.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(toUnitOfMeasureCommand.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {

        UnitOfMeasureCommand unitOfMeasureCommand = null;
        {
            UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
            unitOfMeasure.setId(LONG_VALUE);
            unitOfMeasure.setDescription(DESCRIPTION);

            unitOfMeasureCommand = toUnitOfMeasureCommand.convert(unitOfMeasure);
        }

        //then
        assertNotNull(unitOfMeasureCommand);
        assertEquals(LONG_VALUE, unitOfMeasureCommand.getId());
        assertEquals(DESCRIPTION, unitOfMeasureCommand.getDescription());
    }
}