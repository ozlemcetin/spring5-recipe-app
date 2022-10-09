package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientCommandToIngredientTest {

    public static final Long ID_VALUE = Long.valueOf(1L);
    public static final String DESCRIPTION = "Cheeseburger";
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Long UOM_ID = Long.valueOf(2L);
    IngredientCommandToIngredient commandToObj;

    @BeforeEach
    public void setUp() throws Exception {
        commandToObj = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(commandToObj.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(commandToObj.convert(new IngredientCommand()));
    }

    @Test
    public void convert() throws Exception {

        //given
        IngredientCommand command = new IngredientCommand();
        {
            command.setId(ID_VALUE);
            command.setDescription(DESCRIPTION);
            command.setAmount(AMOUNT);

            UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
            {
                unitOfMeasureCommand.setId(UOM_ID);
                unitOfMeasureCommand.setDescription(null);
            }
            command.setUnitOfMeasureCommand(unitOfMeasureCommand);
        }


        //when
        Ingredient obj = commandToObj.convert(command);

        //then
        assertNotNull(obj);
        assertEquals(ID_VALUE, obj.getId());
        assertEquals(DESCRIPTION, obj.getDescription());
        assertEquals(AMOUNT, obj.getAmount());

        assertNotNull(obj.getUnitOfMeasure());
        assertEquals(UOM_ID, obj.getUnitOfMeasure().getId());
    }

    @Test
    public void convertWithNullUOM() throws Exception {

        //given
        IngredientCommand command = new IngredientCommand();
        {
            command.setId(ID_VALUE);
            command.setDescription(DESCRIPTION);
            command.setAmount(AMOUNT);

            command.setUnitOfMeasureCommand(null);
        }

        //when
        Ingredient obj = commandToObj.convert(command);

        //then
        assertNotNull(obj);
        assertEquals(ID_VALUE, obj.getId());
        assertEquals(DESCRIPTION, obj.getDescription());
        assertEquals(AMOUNT, obj.getAmount());

        assertNull(obj.getUnitOfMeasure());
    }

}