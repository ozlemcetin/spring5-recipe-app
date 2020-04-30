package guru.springframework.spring5recipeapp.converters;

import com.sun.istack.Nullable;
import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {

        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        {
            ingredient.setId(source.getId());
            ingredient.setDescription(source.getDescription());
            ingredient.setAmount(source.getAmount());

            //converter
            ingredient.setUom(uomConverter.convert(source.getUnitOfMeasureCommand()));
        }

        return ingredient;
    }
}
