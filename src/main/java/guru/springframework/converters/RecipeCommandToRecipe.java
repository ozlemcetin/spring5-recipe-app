package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter, IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {

        if (source == null) return null;

        final Recipe recipe = new Recipe();
        {
            recipe.setId(source.getId());
            recipe.setDescription(source.getDescription());
            recipe.setPrepTime(source.getPrepTime());
            recipe.setCookTime(source.getCookTime());
            recipe.setServings(source.getServings());
            recipe.setSource(source.getSource());
            recipe.setUrl(source.getUrl());
            recipe.setDirections(source.getDirections());
            recipe.setDifficulty(source.getDifficulty());
            //recipe.setImage(null);

            recipe.setNotes(notesConverter.convert(source.getNotesCommand()));

            if (source.getIngredientCommands() != null && source.getIngredientCommands().size() > 0) {
                source.getIngredientCommands().forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
            }

            if (source.getCategoryCommands() != null && source.getCategoryCommands().size() > 0) {
                source.getCategoryCommands().forEach(category -> recipe.getCategories().add(categoryConveter.convert(category)));
            }
        }
        return recipe;
    }
}
