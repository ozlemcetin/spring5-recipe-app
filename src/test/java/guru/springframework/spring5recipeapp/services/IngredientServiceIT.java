package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class IngredientServiceIT {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    void findCommandByRecipeIdIngredientId() {

        //Given
        Long recipeId = 1L;
        Long ingredientId = 1L;

        //When Service
        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdIngredientId(recipeId, ingredientId);

        //Then
        assertNotNull(ingredientCommand);
        assertEquals(recipeId, ingredientCommand.getRecipeId());
        assertEquals(ingredientId, ingredientCommand.getId());
    }

    @Test
    @Transactional
    void saveIngredientCommand_save() {

        //Given
        Long recipeId = 1L;
        Long ingredientId = 15L;
        String description = "New Ingredient Description";
        BigDecimal amount = new BigDecimal(5);
        String uomDescription = "Teaspoon";

        IngredientCommand ingredientCommand = null;
        {
            ingredientCommand = new IngredientCommand();
            ingredientCommand.setRecipeId(recipeId);
            ingredientCommand.setId(ingredientId);
            ingredientCommand.setDescription(description);
            ingredientCommand.setAmount(amount);

            //setUnitOfMeasureCommand
            {
                Optional<UnitOfMeasure> optionalUnitOfMeasure =
                        unitOfMeasureRepository.findByDescription(uomDescription);

                UnitOfMeasureCommand unitOfMeasureCommand
                        = toUnitOfMeasureCommand.convert(optionalUnitOfMeasure.get());

                ingredientCommand.setUnitOfMeasureCommand(unitOfMeasureCommand);
            }
        }


        //When Service
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //Then
        assertNotNull(savedIngredientCommand);
        assertEquals(recipeId, ingredientCommand.getRecipeId());
        assertEquals(ingredientId, ingredientCommand.getId());
        assertEquals(amount, ingredientCommand.getAmount());
        assertEquals(uomDescription, ingredientCommand.getUnitOfMeasureCommand().getDescription());
    }

    void saveIngredientCommand_update() {

        //Given
        Long recipeId = 1L;
        Long ingredientId = 1L;
        String description = "New Ingredient Description";
        BigDecimal amount = new BigDecimal(5);
        Long uomId = 1L;

        IngredientCommand ingredientCommand = null;
        {
            ingredientCommand = new IngredientCommand();
            ingredientCommand.setRecipeId(recipeId);
            ingredientCommand.setId(ingredientId);
            ingredientCommand.setDescription(description);
            ingredientCommand.setAmount(amount);

            //setUnitOfMeasureCommand
            {

                UnitOfMeasureCommand unitOfMeasureCommand
                        = new UnitOfMeasureCommand();
                unitOfMeasureCommand.setId(uomId);

                ingredientCommand.setUnitOfMeasureCommand(unitOfMeasureCommand);
            }
        }


        //When Service
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //Then
        assertNotNull(savedIngredientCommand);
        assertEquals(recipeId, ingredientCommand.getRecipeId());
        assertEquals(ingredientId, ingredientCommand.getId());
        assertEquals(amount, ingredientCommand.getAmount());
        assertEquals(uomId, ingredientCommand.getUnitOfMeasureCommand().getId());
    }


}