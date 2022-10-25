package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe commandToRecipe;
    private final RecipeToRecipeCommand recipeToCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe commandToRecipe, RecipeToRecipeCommand recipeToCommand) {
        this.recipeRepository = recipeRepository;
        this.commandToRecipe = commandToRecipe;
        this.recipeToCommand = recipeToCommand;
    }


    @Override
    public Set<Recipe> getRecipes() {

        Set<Recipe> recipes = new HashSet<>();
        {
            log.debug("RecipeServiceImpl fetching all recipes from the repository");
            //recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
            recipeRepository.findAll().forEach(recipes::add);
        }

        return recipes;
    }

    @Override
    public Recipe findById(Long id) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            // throw new RuntimeException("Recipe Not Found!");
            throw new NotFoundException("Recipe Not Found!");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {

        Recipe recipe = findById(id);
        return recipeToCommand.convert(recipe);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {

        Recipe recipe = commandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(recipe);
        log.info("Saved Recipe Id :" + savedRecipe.getId());

        return recipeToCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {

        log.info("Deleting Recipe Id :" + id);
        recipeRepository.deleteById(id);
    }
}
