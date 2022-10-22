package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @Mock
    private RecipeService recipeService;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private Model model;

    private IngredientController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        //controller
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);

        //mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listIngredients() throws Exception {

        //given
        Long id = 1L;
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(id);

            //when
            Mockito.when(recipeService.findCommandById(anyLong())).thenReturn(command);
        }

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + id + "/ingredients"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));

        //verify
        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(anyLong());

    }

    @Test
    public void showRecipeIngredient() throws Exception {

        //given
        Long ingredientId = 3L;
        Long recipeId = 1L;
        {
            IngredientCommand command = new IngredientCommand();
            command.setId(ingredientId);
            command.setRecipeId(recipeId);

            //when
            Mockito.when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);
        }

        //then
        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"))

                .andExpect(status().isOk())

                .andExpect(view().name("recipe/ingredient/show"))

                .andExpect(model().attributeExists("ingredientCommand"));

        //verify
        Mockito.verify(ingredientService, Mockito.times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void testNewIngredientForm() throws Exception {

        //given
        Long recipeId = 1L;
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(recipeId);

            //when
            when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
            //when
            when(unitOfMeasureService.listAllUnitOfMeasure()).thenReturn(new HashSet<>());
        }

        //then
        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/new"))

                .andExpect(status().isOk())

                .andExpect(view().name("recipe/ingredient/ingredientform"))

                .andExpect(model().attributeExists("ingredientCommand"))

                .andExpect(model().attributeExists("listAllUnitOfMeasure"));

        //verify
        verify(recipeService, times(1)).findCommandById(anyLong());
        verify(unitOfMeasureService, times(1)).listAllUnitOfMeasure();
    }


    @Test
    public void updateRecipeIngredient() throws Exception {

        //given
        Long ingredientId = 3L;
        Long recipeId = 1L;
        {
            //when
            when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(new IngredientCommand());

            //when
            when(unitOfMeasureService.listAllUnitOfMeasure()).thenReturn(new HashSet<>());
        }

        //then
        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/update"))

                .andExpect(status().isOk())

                .andExpect(view().name("recipe/ingredient/ingredientform"))

                .andExpect(model().attributeExists("ingredientCommand"))

                .andExpect(model().attributeExists("listAllUnitOfMeasure"));
    }

    @Test
    public void saveOrUpdate() throws Exception {

        //given
        Long ingredientId = 3L;
        Long recipeId = 1L;
        {
            IngredientCommand command = new IngredientCommand();
            command.setId(ingredientId);
            command.setRecipeId(recipeId);

            //when
            when(ingredientService.saveIngredientCommand(any())).thenReturn(command);
        }

        //then
        mockMvc.perform(post("/recipe/" + recipeId + "/ingredient").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        //.param("id", "")
                        //.param("description", "some string")
                )

                .andExpect(status().is3xxRedirection())

                .andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"));
    }

    @Test
    public void testDeleteIngredient() throws Exception {

        //given
        Long recipeId = 1L;
        Long ingredientId = 3L;

        //then
        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/delete"))

                .andExpect(status().is3xxRedirection())

                .andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());
    }

}