package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
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
    private final RecipeCommandToRecipe toRecipe;
    private final RecipeToRecipeCommand toRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe toRecipe, RecipeToRecipeCommand toRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.toRecipe = toRecipe;
        this.toRecipeCommand = toRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        log.debug("RecipeServiceImpl > Recipe List Size:" + recipeSet.size());
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {

        Optional<Recipe> recipeOptional =
                recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            // throw new RuntimeException("Recipe Not Found! Id :" + id);
            throw new NotFoundException("Recipe Not Found! For Id Value:" + id);
        }

        return recipeOptional.get();
    }

    /*
    @Transactional is used as we are doing a conversion outside the scope.
     */
    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {

        Recipe recipe = findById(id);
        return toRecipeCommand.convert(recipe);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {

        Recipe savedRecipe = null;
        {
            Recipe recipe = toRecipe.convert(recipeCommand);
            savedRecipe = recipeRepository.save(recipe);
            log.debug("Saved Recipe Id: " + savedRecipe.getId());
        }

        return toRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
