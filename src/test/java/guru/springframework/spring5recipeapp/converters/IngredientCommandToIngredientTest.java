package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientCommandToIngredientTest {

    IngredientCommandToIngredient commandToIngredient;

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "Description";
    public static final BigDecimal AMOUNT = new BigDecimal("1");


    @Before
    public void setUp() throws Exception {
        commandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void convert_null() {
        assertNull(commandToIngredient.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(commandToIngredient.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {

        Long uomc_id = 2L;
        Ingredient ingredient = null;
        {
            IngredientCommand ingredientCommand = new IngredientCommand();
            ingredientCommand.setId(ID_VALUE);
            ingredientCommand.setDescription(DESCRIPTION);
            ingredientCommand.setAmount(AMOUNT);

            //UnitOfMeasureCommand
            UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
            unitOfMeasureCommand.setId(uomc_id);
            ingredientCommand.setUnitOfMeasureCommand(unitOfMeasureCommand);

            ingredient = commandToIngredient.convert(ingredientCommand);
        }

        assertNotNull(ingredient);
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());

        //UnitOfMeasure
        assertNotNull(ingredient.getUom());
        assertEquals(uomc_id, ingredient.getUom().getId());
    }

    public void convert_nullUOM() throws Exception {

        Ingredient ingredient = null;
        {
            IngredientCommand ingredientCommand = new IngredientCommand();
            ingredientCommand.setId(ID_VALUE);
            ingredientCommand.setDescription(DESCRIPTION);
            ingredientCommand.setAmount(AMOUNT);

            ingredient = commandToIngredient.convert(ingredientCommand);
        }

        assertNotNull(ingredient);
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());

        assertNull(ingredient.getUom());
    }
}