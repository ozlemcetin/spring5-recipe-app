package guru.springframework.spring5recipeapp.bootstrap;

import guru.springframework.spring5recipeapp.domain.*;
import guru.springframework.spring5recipeapp.repositories.CategoryRepository;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
@Profile("default")
public class RecipeBootstrapH2 implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrapH2(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


        /*
        For Lazy Initialization Exception; In and out of Hibernate transaction,
        lazy collections need to get initialized within a transaction and within the same Hibernate session.
        Very easy way around is, @Transactional
        Spring Framework, creates a transaction around this method.
        Everything is going to happen in the same transactional context
         */
        log.debug("RecipeBootstrapH2 > Loading Bootstarp Data.");
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {

        //UnitOfMeasure
        UnitOfMeasure eachUom = getUnitOfMeasureByDescription("Each");
        UnitOfMeasure tableSpoonUom = getUnitOfMeasureByDescription("Tablespoon");
        UnitOfMeasure teaSpoonUom = getUnitOfMeasureByDescription("Teaspoon");
        UnitOfMeasure dashUom = getUnitOfMeasureByDescription("Dash");
        UnitOfMeasure pintUom = getUnitOfMeasureByDescription("Pint");
        UnitOfMeasure cupsUom = getUnitOfMeasureByDescription("Cup");

        //Category
        Category americanCategory = getCategoryByDescription("American");
        Category mexicanCategory = getCategoryByDescription("Mexican");

        //List
        List<Recipe> recipes = new ArrayList<>();

        //Yummy Guac
        {
            Recipe guacRecipe = new Recipe();

            //Long id;
            guacRecipe.setDescription("Perfect Guacamole");
            guacRecipe.setPrepTime(10);
            guacRecipe.setCookTime(0);
            guacRecipe.setServings(4);
            guacRecipe.setSource("Simply Recipes");
            guacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");

            guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                    "\n" +
                    "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                    "\n" +
                    "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                    "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                    "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                    "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                    "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                    "\n" +
                    "\n" +
                    "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

            {
                /*
                  Bi-Directional Relationship - addIngredient
                 */

                //2 ripe avocados
                guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));

                //1 / 4 teaspoon of salt, more to taste
                guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaSpoonUom));

                // 1 tablespoon fresh lime juice or lemon juice
                guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom));

                // 2 tablespoons to 1 / 4 cup of minced red onion or thinly sliced green onion
                guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));

                // 1 - 2 serrano chiles, stems and seeds removed, minced
                guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));

                // 2 tablespoons cilantro (leaves and tender stems),finely chopped
                guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom));

                // A dash of freshly grated black pepper
                guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));

                //1 / 2 ripe tomato, seeds and pulp removed, chopped
                guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom));
            }


            //Byte[] image;
            guacRecipe.setDifficulty(Difficulty.EASY);


            {
                Notes notes = new Notes();
                notes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                        "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                        "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                        "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                        "\n" +
                        "\n" +
                        "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");

                /*
                  Bi-Directional Relationship - addNotes
                  notes.setRecipe(tacosRecipe);
                  tacosRecipe.setNotes(notes);
                 */
                guacRecipe.setNotes(notes);
            }

            {
                guacRecipe.getCategories().add(americanCategory);
                guacRecipe.getCategories().add(mexicanCategory);
            }

            //Add to list
            recipes.add(guacRecipe);
        }

        //Yummy Tacos
        {
            Recipe tacosRecipe = new Recipe();

            //Long id;
            tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
            tacosRecipe.setPrepTime(20);
            tacosRecipe.setCookTime(9);
            tacosRecipe.setServings(4);
            tacosRecipe.setSource("Simply Recipes");
            tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

            tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                    "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                    "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                    "\n" +
                    "\n" +
                    "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                    "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                    "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                    "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                    "\n" +
                    "\n" +
                    "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");


            {
                Set<Ingredient> ingredients = new HashSet<>();

                ingredients.add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom, tacosRecipe));
                ingredients.add(new Ingredient("Dried Oregano", new BigDecimal(1), teaSpoonUom, tacosRecipe));
                ingredients.add(new Ingredient("Dried Cumin", new BigDecimal(1), teaSpoonUom, tacosRecipe));
                ingredients.add(new Ingredient("Sugar", new BigDecimal(1), teaSpoonUom, tacosRecipe));
                ingredients.add(new Ingredient("Salt", new BigDecimal(".5"), teaSpoonUom, tacosRecipe));
                ingredients.add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom, tacosRecipe));
                ingredients.add(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom, tacosRecipe));
                ingredients.add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom, tacosRecipe));
                ingredients.add(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom, tacosRecipe));
                ingredients.add(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom, tacosRecipe));
                ingredients.add(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom, tacosRecipe));
                ingredients.add(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom, tacosRecipe));
                ingredients.add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom, tacosRecipe));
                ingredients.add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom, tacosRecipe));
                ingredients.add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom, tacosRecipe));
                ingredients.add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom, tacosRecipe));
                ingredients.add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom, tacosRecipe));
                ingredients.add(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom, tacosRecipe));
                ingredients.add(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom, tacosRecipe));

                tacosRecipe.setIngredients(ingredients);
            }


            //Byte[] image;
            tacosRecipe.setDifficulty(Difficulty.MODERATE);


            {
                Notes notes = new Notes();
                notes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                        "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                        "Today???s tacos are more purposeful ??? a deliberate meal instead of a secretive midnight snack!\n" +
                        "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                        "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                        "\n" +
                        "\n" +
                        "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");

                /*
                  Bi-Directional Relationship - addNotes
                  notes.setRecipe(tacosRecipe);
                  tacosRecipe.setNotes(notes);
                 */
                tacosRecipe.setNotes(notes);
            }


            {
                tacosRecipe.getCategories().add(americanCategory);
                tacosRecipe.getCategories().add(mexicanCategory);
            }

            //Add to list
            recipes.add(tacosRecipe);
        }


        return recipes;
    }

    private UnitOfMeasure getUnitOfMeasureByDescription(String description) {

        //Spring Data JPA Query Method
        Optional<UnitOfMeasure> optionalUnitOfMeasure =
                unitOfMeasureRepository.findByDescription(description);

        if (!optionalUnitOfMeasure.isPresent()) {
            throw new RuntimeException("Expected UnitOfMeasure Not Found!");
        }

        return optionalUnitOfMeasure.get();
    }

    private Category getCategoryByDescription(String description) {

        //Spring Data JPA Query Method
        Optional<Category> optionalCategory =
                categoryRepository.findByDescription(description);

        if (!optionalCategory.isPresent()) {
            throw new RuntimeException("Expected Category Not Found!");
        }

        return optionalCategory.get();
    }


}
