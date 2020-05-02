package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {


    private final RecipeService recipeService;
    private final IngredientToIngredientCommand toIngredientCommand;

    public IngredientServiceImpl(RecipeService recipeService, IngredientToIngredientCommand toIngredientCommand) {
        this.recipeService = recipeService;
        this.toIngredientCommand = toIngredientCommand;
    }


    @Override
    public IngredientCommand findCommandByRecipeIdIngredientId(Long recipeId, Long ingredientId) {

        Recipe recipe = recipeService.findById(recipeId);

        Optional<IngredientCommand> optionalIngredientCommand =
                recipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getId().equals(ingredientId))
                        .map(ingredient -> toIngredientCommand.convert(ingredient))
                        .findFirst();

        if (!optionalIngredientCommand.isPresent()) {
            throw new RuntimeException("Ingredient Not Found! Id:" + ingredientId);
        }

        return optionalIngredientCommand.get();

    }
}
