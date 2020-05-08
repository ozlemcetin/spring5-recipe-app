package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import guru.springframework.spring5recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {

        log.debug("Getting ingredient list for recipe id: " + recipeId);

        /*
        Use command object to avoid lazy load errors in thymeleaf
         */
        model.addAttribute("recipeCommand",
                recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId,
                                       Model model) {

        IngredientCommand ingredientCommand =
                ingredientService.findCommandByRecipeIdIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        model.addAttribute("ingredientCommand", ingredientCommand);

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {

        //Recipe Id Valid
        RecipeCommand recipeCommand =
                recipeService.findCommandById(Long.valueOf(recipeId));

        if (recipeCommand == null) {
            //todo raise exception if not
        }

        {
            IngredientCommand ingredientCommand = new IngredientCommand();

            //need to return back parent id for hidden form property
            ingredientCommand.setRecipeId(Long.valueOf(recipeId));

            //init uom
            ingredientCommand.setUnitOfMeasureCommand(new UnitOfMeasureCommand());

            model.addAttribute("ingredientCommand", ingredientCommand);
        }

        {
            Set<UnitOfMeasureCommand> unitOfMeasureCommands =
                    unitOfMeasureService.listAllUnitOfMeasureCommands();

            model.addAttribute("unitOfMeasureCommands", unitOfMeasureCommands);
        }


        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId,
                                         Model model) {

        IngredientCommand ingredientCommand =
                ingredientService.findCommandByRecipeIdIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        model.addAttribute("ingredientCommand", ingredientCommand);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands =
                unitOfMeasureService.listAllUnitOfMeasureCommands();
        model.addAttribute("unitOfMeasureCommands", unitOfMeasureCommands);

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {

        IngredientCommand savedIngredientCommand =
                ingredientService.saveIngredientCommand(ingredientCommand);

        Long savedRecipeId = savedIngredientCommand.getRecipeId();
        log.debug("Saved Ingredient Receipe Id:" + savedRecipeId);

        Long savedIngredientId = savedIngredientCommand.getId();
        log.debug("Saved Ingredient Id:" + savedIngredientId);

        return "redirect:/recipe/" + savedRecipeId + "/ingredient/" + savedIngredientId + "/show";
    }

}
