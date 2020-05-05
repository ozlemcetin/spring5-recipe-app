package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
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


    /*
     @Transactional is used as we are doing a conversion outside the scope.
     */
    @Override
    @Transactional
    public IngredientCommand findCommandByRecipeIdIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> optionalIngredient
                = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if (!optionalIngredient.isPresent()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return toIngredientCommand.convert(optionalIngredient.get());
    }


    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Long recipeId = ingredientCommand.getRecipeId();
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {

            //todo impl error handling
            log.error("Recipe not found for id: " + recipeId);
            return new IngredientCommand();

        } else {

            Recipe recipe = recipeOptional.get();

            Long ingredientId = ingredientCommand.getId();
            Optional<Ingredient> optionalIngredient
                    = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (optionalIngredient.isPresent()) {

                //Update
                Ingredient ingredient = optionalIngredient.get();

                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setAmount(ingredientCommand.getAmount());

                Long uomId = ingredientCommand.getUnitOfMeasureCommand().getId();

                //todo impl error handling
                UnitOfMeasure unitOfMeasure =
                        unitOfMeasureRepository.findById(uomId)
                                .orElseThrow(() -> new RuntimeException("UnitOfMeasure Not Found! Id :" + uomId));

                ingredient.setUom(unitOfMeasure);

            } else {

                //addIngredient
                Ingredient ingredient = toIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
            }


            //Save
            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedOptionalIngredient
                    = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (!savedOptionalIngredient.isPresent()) {
                //todo impl error handling
                log.error("Ingredient id not found: " + ingredientId);
            }

            return toIngredientCommand.convert(savedOptionalIngredient.get());

        }

    }
}
