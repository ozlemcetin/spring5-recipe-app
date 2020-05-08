package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import guru.springframework.spring5recipeapp.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientControllerTest {

    IngredientController ingredientController;

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        //ingredientController
        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);

        //mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

    }

    @Test
    public void listIngredients_MockMvc() throws Exception {

        //given
        Long recipeId = 1L;
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(recipeId);
            when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        }

        //when
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

        //given
        Long recipeId = 1L;
        Long ingredientId = 1L;
        {
            IngredientCommand ingredientCommand = new IngredientCommand();
            ingredientCommand.setRecipeId(recipeId);
            ingredientCommand.setId(ingredientId);
            when(ingredientService.findCommandByRecipeIdIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredientCommand"));

        //verify
        {
            verify(ingredientService).findCommandByRecipeIdIngredientId(anyLong(), anyLong());
        }
    }

    @Test
    public void newIngredient() throws Exception {

        //given
        Long recipeId = 1L;
        //Long ingredientId = 2L;
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(recipeId);
            when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        }
        {
            Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
            when(unitOfMeasureService.listAllUnitOfMeasureCommands()).thenReturn(unitOfMeasureCommands);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/ingredient/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredientCommand"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("unitOfMeasureCommands"));

        //verify
        {
            verify(recipeService).findCommandById(anyLong());
            verify(unitOfMeasureService).listAllUnitOfMeasureCommands();
        }

    }

    @Test
    public void updateRecipeIngredient() throws Exception {

        //given
        Long recipeId = 1L;
        Long ingredientId = 2L;
        {
            IngredientCommand ingredientCommand = new IngredientCommand();
            ingredientCommand.setRecipeId(recipeId);
            ingredientCommand.setId(ingredientId);
            when(ingredientService.findCommandByRecipeIdIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        }
        {
            Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
            when(unitOfMeasureService.listAllUnitOfMeasureCommands()).thenReturn(unitOfMeasureCommands);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredientCommand"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("unitOfMeasureCommands"));

        //verify
        {
            verify(ingredientService).findCommandByRecipeIdIngredientId(anyLong(), anyLong());
            verify(unitOfMeasureService).listAllUnitOfMeasureCommands();
        }
    }

    @Test
    public void saveOrUpdate() throws Exception {

        //given
        Long recipeId = 1L;
        Long ingredientId = 2L;
        {
            IngredientCommand ingredientCommand = new IngredientCommand();
            ingredientCommand.setRecipeId(recipeId);
            ingredientCommand.setId(ingredientId);
            when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/" + recipeId + "/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "new description")
                .param("amount", "5"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(
                        "redirect:/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"));

        {
            verify(ingredientService).saveIngredientCommand(any());
        }
    }


    @Test
    public void deleteRecipeIngredient() throws Exception {

        //given
        Long recipeId = 1L;
        Long ingredientId = 2L;

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/" + recipeId + "/ingredients"));

        //verify
        {
            verify(ingredientService).deleteByRecipeIdIngredientId(anyLong(), anyLong());
        }
    }
}