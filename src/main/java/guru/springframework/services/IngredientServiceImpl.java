package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    private final IngredientToIngredientCommand toIngredientCommand;


    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand toIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.toIngredientCommand = toIngredientCommand;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        //todo impl error handling
        if (!recipeOptional.isPresent()) {
            log.error("Recipe id not found: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()

                .filter(ingredient -> ingredient.getId().equals(ingredientId))

                .map(ingredient -> toIngredientCommand.convert(ingredient))

                .findFirst();

        //todo impl error handling
        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }
}
