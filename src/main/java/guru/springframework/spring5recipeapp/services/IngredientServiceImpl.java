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
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand toIngredientCommand;
    private final IngredientCommandToIngredient toIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand toIngredientCommand, IngredientCommandToIngredient toIngredient) {

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

            String icDescription = ingredientCommand.getDescription();
            BigDecimal icAmount = ingredientCommand.getAmount();
            Long icUOMId = ingredientCommand.getUnitOfMeasureCommand().getId();

            if (optionalIngredient.isPresent()) {

                //Update
                Ingredient ingredient = optionalIngredient.get();

                ingredient.setDescription(icDescription);
                ingredient.setAmount(icAmount);

                //todo impl error handling
                UnitOfMeasure unitOfMeasure =
                        unitOfMeasureRepository.findById(icUOMId)
                                .orElseThrow(() -> new RuntimeException("UnitOfMeasure Not Found! Id :" + icUOMId));

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
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientId))
                    .findFirst();

            if (!savedOptionalIngredient.isPresent()) {
                //Brand new ingredient doesnt have an id value
                savedOptionalIngredient
                        = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(icDescription))
                        .filter(ingredient -> ingredient.getAmount().equals(icAmount))
                        .filter(ingredient -> ingredient.getUom().getId().equals(icUOMId))
                        .findFirst();
            }

            return toIngredientCommand.convert(savedOptionalIngredient.get());

        }

    }

    @Override
    public Recipe deleteByRecipeIdIngredientId(Long recipeId, Long ingredientId) {

        //Check the values are correct
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

        Recipe savedRecipe = null;
        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            recipe.getIngredients().remove(ingredient);
            ingredient.setRecipe(null);

            //Save
            savedRecipe = recipeRepository.save(recipe);
        }

        return savedRecipe;
    }
}
