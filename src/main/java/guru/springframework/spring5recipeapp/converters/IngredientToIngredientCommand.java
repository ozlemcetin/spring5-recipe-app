package guru.springframework.spring5recipeapp.converters;

import com.sun.istack.Nullable;
import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {

        if (source == null) {
            return null;
        }

        final IngredientCommand ingredientCommand = new IngredientCommand();
        {
            ingredientCommand.setId(source.getId());
            ingredientCommand.setDescription(source.getDescription());
            ingredientCommand.setAmount(source.getAmount());

            //converter
            ingredientCommand.setUnitOfMeasureCommand(uomConverter.convert(source.getUom()));
        }

        return ingredientCommand;
    }
}
