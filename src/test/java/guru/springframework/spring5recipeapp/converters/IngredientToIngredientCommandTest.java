package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientToIngredientCommandTest {

    IngredientToIngredientCommand toIngredientCommand;

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "Description";
    public static final BigDecimal AMOUNT = new BigDecimal("1");

    @Before
    public void setUp() throws Exception {
        toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void convert_null() {
        assertNull(toIngredientCommand.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(toIngredientCommand.convert(new Ingredient()));
    }


    @Test
    public void convert() {

        Long uom_id = 2L;
        IngredientCommand ingredientCommand = null;
        {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(ID_VALUE);
            ingredient.setDescription(DESCRIPTION);
            ingredient.setAmount(AMOUNT);

            //UnitOfMeasure
            UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
            unitOfMeasure.setId(uom_id);
            ingredient.setUom(unitOfMeasure);

            ingredientCommand = toIngredientCommand.convert(ingredient);
        }


        assertNotNull(ingredientCommand);
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());

        //UnitOfMeasure
        assertNotNull(ingredientCommand.getUnitOfMeasureCommand());
        assertEquals(uom_id, ingredientCommand.getUnitOfMeasureCommand().getId());
    }

    public void convert_nullUOM() throws Exception {

        IngredientCommand ingredientCommand = null;
        {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(ID_VALUE);
            ingredient.setDescription(DESCRIPTION);
            ingredient.setAmount(AMOUNT);

            ingredientCommand = toIngredientCommand.convert(ingredient);
        }

        assertNotNull(ingredientCommand);
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());


        assertNull(ingredientCommand.getUnitOfMeasureCommand());
    }
}