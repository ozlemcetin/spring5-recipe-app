package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jt on 6/1/17.
 */
@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/recipes", "/recipes.html"})
    public String getIndexPage(Model model) {

        log.debug("RecipeController > Returns Recipes Page.");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes";
    }
}
