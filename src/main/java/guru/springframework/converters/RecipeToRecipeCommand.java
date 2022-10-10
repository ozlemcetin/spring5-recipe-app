package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConveter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConveter, IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {

        if (source == null) return null;

        final RecipeCommand command = new RecipeCommand();
        {

            command.setId(source.getId());
            command.setDescription(source.getDescription());
            command.setPrepTime(source.getPrepTime());
            command.setCookTime(source.getCookTime());
            command.setServings(source.getServings());
            command.setSource(source.getSource());
            command.setUrl(source.getUrl());
            command.setDirections(source.getDirections());
            command.setDifficulty(source.getDifficulty());

            command.setNotesCommand(notesConverter.convert(source.getNotes()));

            if (source.getIngredients() != null && source.getIngredients().size() > 0) {
                source.getIngredients().forEach(ingredient -> command.getIngredientCommands().add(ingredientConverter.convert(ingredient)));
            }

            if (source.getCategories() != null && source.getCategories().size() > 0) {
                source.getCategories().forEach((Category category) -> command.getCategoryCommands().add(categoryConveter.convert(category)));
            }
        }

        return command;
    }
}
