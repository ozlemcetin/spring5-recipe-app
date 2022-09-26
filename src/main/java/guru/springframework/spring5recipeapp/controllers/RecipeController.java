package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /*
        To pick up the id value out of the URL, use {id} in the @RequestMapping path value and
        @PathVariable String id in the method declaration
     */
    @RequestMapping(path = "/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

}
