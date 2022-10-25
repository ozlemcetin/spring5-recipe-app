package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    public static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /*
        To pick up the id value out of the URL, use {id} in the @RequestMapping path value and
        @PathVariable String id in the method declaration
     */
    @GetMapping(path = "/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    @GetMapping(path = "/recipe/new")
    public String newRecipe(Model model) {

        model.addAttribute("recipeCommand", new RecipeCommand());
        return RECIPE_RECIPEFORM_URL;
    }


    @PostMapping("/recipe")
    //@RequestMapping(name = "/recipe", method = RequestMethod.POST)
    public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipeCommand") RecipeCommand command, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return RECIPE_RECIPEFORM_URL;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping(path = "/recipe/{id}/update")
    public String updateById(@PathVariable String id, Model model) {

        model.addAttribute("recipeCommand", recipeService.findCommandById(Long.valueOf(id)));
        return RECIPE_RECIPEFORM_URL;
    }


    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {

        log.debug("Deleting recipe id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

}
