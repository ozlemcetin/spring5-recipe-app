package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientControllerTest {

    IngredientController ingredientController;

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        //ingredientController
        ingredientController = new IngredientController(recipeService, ingredientService);

        //mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

    }

    @Test
    public void listIngredients_MockMvc() throws Exception {

        //when
        Long recipeId = 1L;
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(recipeId);
            when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));

        //verify
        {
            verify(recipeService).findCommandById(anyLong());
        }

    }


    @Test
    public void showRecipeIngredient() throws Exception {

        //when
        Long recipeId = 1L;
        Long ingredientId = 1L;
        {
            IngredientCommand ingredientCommand = new IngredientCommand();
            ingredientCommand.setRecipeId(recipeId);
            ingredientCommand.setId(ingredientId);
            when(ingredientService.findCommandByRecipeIdIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredientCommand"));

        //verify
        {
            verify(ingredientService).findCommandByRecipeIdIngredientId(anyLong(), anyLong());
        }
    }
}