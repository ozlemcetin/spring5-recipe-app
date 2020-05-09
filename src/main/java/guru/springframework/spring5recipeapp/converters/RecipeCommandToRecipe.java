package guru.springframework.spring5recipeapp.converters;


import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter, IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {

        if (source == null) {
            return null;
        }

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

            //converter
            if (source.getIngredientCommands() != null && source.getIngredientCommands().size() > 0) {
                source.getIngredientCommands()
                        .forEach(ingredientCommand -> recipe.getIngredients().add(ingredientConverter.convert(ingredientCommand)));
            }

            //Image is right now is one-way; only toRecipeCommand available
            //recipe.setImage(source.getImage());

            recipe.setDifficulty(source.getDifficulty());

            //converter
            recipe.setNotes(notesConverter.convert(source.getNotesCommand()));

            //converter
            if (source.getCategoryCommands() != null && source.getCategoryCommands().size() > 0) {
                source.getCategoryCommands()
                        .forEach(categoryCommand -> recipe.getCategories().add(categoryConveter.convert(categoryCommand)));
            }

        }

        return recipe;
    }
}
