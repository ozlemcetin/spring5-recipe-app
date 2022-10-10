package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jt on 6/21/17.
 */
public class IngredientToIngredientCommandTest {

    public static final Long ID_VALUE = Long.valueOf(1L);
    public static final String DESCRIPTION = "Cheeseburger";
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Long UOM_ID = Long.valueOf(2L);


    IngredientToIngredientCommand objToCommand;

    @BeforeEach
    public void setUp() throws Exception {
        objToCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullConvert() throws Exception {
        assertNull(objToCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(objToCommand.convert(new Ingredient()));
    }

    @Test
    public void convert() throws Exception {

        //given
        Ingredient obj = new Ingredient();
        {
            obj.setId(ID_VALUE);
            obj.setDescription(DESCRIPTION);
            obj.setAmount(AMOUNT);

            UnitOfMeasure uom = new UnitOfMeasure();
            {
                uom.setId(UOM_ID);
                uom.setDescription(null);
            }
            obj.setUnitOfMeasure(uom);

            obj.setRecipe(null);
        }

        //when
        IngredientCommand command = objToCommand.convert(obj);

        //then
        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(AMOUNT, command.getAmount());

        assertNotNull(command.getUnitOfMeasureCommand());
        assertEquals(UOM_ID, command.getUnitOfMeasureCommand().getId());

    }

    @Test
    public void convertWithNullUOM() throws Exception {

        //given
        Ingredient ingredient = new Ingredient();
        {
            ingredient.setId(ID_VALUE);
            ingredient.setDescription(DESCRIPTION);
            ingredient.setAmount(AMOUNT);
            ingredient.setUnitOfMeasure(null);
        }


        //when
        IngredientCommand command = objToCommand.convert(ingredient);

        //then
        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(AMOUNT, command.getAmount());
        assertNull(command.getUnitOfMeasureCommand());
    }


}