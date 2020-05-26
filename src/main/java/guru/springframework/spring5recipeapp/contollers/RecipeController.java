package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Slf4j
@Controller
public class RecipeController {

    //public
    public static final String VIEWS_RECIPE_RECIPE_FORM_URL = "recipe/recipeForm";

    //private
    private final RecipeService recipeService;

    //constructor
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping
    @RequestMapping({"/recipe/{id}/show"})
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model) {

        model.addAttribute("recipeCommand", new RecipeCommand());
        return VIEWS_RECIPE_RECIPE_FORM_URL;

    }

    @GetMapping
    @RequestMapping({"/recipe/{id}/update"})
    public String updateRecipe(@PathVariable String id, Model model) {

        model.addAttribute("recipeCommand", recipeService.findCommandById(Long.valueOf(id)));
        return VIEWS_RECIPE_RECIPE_FORM_URL;
    }

    /*
    @ModelAttribute binds the form post parameters to the Recipe Command Object
     */

    @PostMapping("/recipe")
    //@RequestMapping(name = "recipe", method = RequestMethod.POST)
    //@RequestMapping("/recipe")
    public String saveOrUpdate(@Valid @ModelAttribute RecipeCommand recipeCommand, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> log.debug(error.toString()));
            return VIEWS_RECIPE_RECIPE_FORM_URL;
        }

        RecipeCommand savedRecipeCommand
                = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping({"/recipe/{id}/delete"})
    public String deleteRecipe(@PathVariable String id) {

        log.debug("Deleting Recipe by Id: " + id);
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(Exception exception) {

        log.error("Handling not found exception: " + exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("title", "404 Not Found");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }


}
