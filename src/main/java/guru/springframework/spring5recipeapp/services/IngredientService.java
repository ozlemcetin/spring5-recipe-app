package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;

public interface IngredientService {

    IngredientCommand findCommandByRecipeIdIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    Recipe deleteByRecipeIdIngredientId(Long recipeId, Long ingredientId);
}
