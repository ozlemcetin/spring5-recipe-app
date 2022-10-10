package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final IngredientToIngredientCommand toIngredientCommand;

    private final IngredientCommandToIngredient toIngredient;


    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientToIngredientCommand toIngredientCommand, IngredientCommandToIngredient toIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.toIngredientCommand = toIngredientCommand;
        this.toIngredient = toIngredient;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {

        //todo toss error if not found!
        if (command == null || command.getId() == null || command.getRecipeId() == null) {
            log.error("IngredientCommand obj is null ");
            return null;
        }

        Long ingredientId = command.getId();
        Long recipeId = command.getRecipeId();
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        //todo toss error if not found!
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for id: " + recipeId);
            return null;
        }

        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()

                .filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();


        if (ingredientOptional.isPresent()) {

            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());

            //setUnitOfMeasure
            {
                //todo toss error if not found!
                if (command.getUnitOfMeasureCommand() == null || command.getUnitOfMeasureCommand().getId() == null) {
                    log.error("UnitOfMeasureCommand obj is null ");
                    return null;
                }

                UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(command.getUnitOfMeasureCommand().getId())
                        //todo address this
                        .orElseThrow(() -> new RuntimeException("UnitOfMeasure NOT FOUND"));

                ingredientFound.setUnitOfMeasure(unitOfMeasure);
            }

        } else {

            //add new Ingredient
            recipe.addIngredient(toIngredient.convert(command));
        }

        //save
        Recipe savedRecipe = recipeRepository.save(recipe);

        //todo check for fail
        Ingredient savedIngredient = savedRecipe.getIngredients().stream()

                .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientId)).findFirst().get();

        return toIngredientCommand.convert(savedIngredient);

    }
}
