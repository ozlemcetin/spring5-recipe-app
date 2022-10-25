package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class RecipeControllerTest {


    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    private RecipeController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        //controller
        controller = new RecipeController(recipeService);

        //mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(controller)

                //wiring in controller advice for exception handling
                .setControllerAdvice(new ExceptionHandlingController())

                .build();
    }

    @Test
    public void showById() throws Exception {

        //given
        Long id = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(id);

            //when
            Mockito.when(recipeService.findById(anyLong())).thenReturn(recipe);
        }

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + id + "/show"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/show"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        //verify
        Mockito.verify(recipeService, Mockito.times(1)).findById(anyLong());
    }

    @Test
    public void testGetRecipe_NotFoundException() throws Exception {

        //when
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        //then
        mockMvc.perform(get("/recipe/1/show"))

                .andExpect(status().isNotFound())

                .andExpect(view().name("error"));
    }

    @Test
    public void testGetRecipe_NumberFormatException() throws Exception {

        //then
        mockMvc.perform(get("/recipe/abc/show"))

                .andExpect(status().isBadRequest())

                .andExpect(view().name("error"));
    }

    @Test
    void newRecipe() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name(RecipeController.RECIPE_RECIPEFORM_URL))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));
    }


    @Test
    void saveOrUpdateRecipe() throws Exception {

        //given
        Long id = 1L;
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(id);

            //when
            Mockito.when(recipeService.saveRecipeCommand(any())).thenReturn(command);
        }

        //POST
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")

                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)

                        .param("id", "1")

                        .param("description", "some string")

                        .param("directions", "spme string"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())

                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/" + id + "/show"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));

        //verify
        Mockito.verify(recipeService, Mockito.times(1)).saveRecipeCommand(any());
    }

    @Test
    void saveOrUpdateRecipe_BeanValidationFail() throws Exception {

        //given
        Long id = 1L;
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(id);

            //when
            Mockito.when(recipeService.saveRecipeCommand(any())).thenReturn(command);
        }

        //POST
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")

                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)

                        //validation fails
                        .param("description", ""))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name(RecipeController.RECIPE_RECIPEFORM_URL))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));
    }

    @Test
    void updateById() throws Exception {

        //given
        Long id = 1L;
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(id);

            //when
            Mockito.when(recipeService.findCommandById(anyLong())).thenReturn(command);
        }

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + id + "/update"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name(RecipeController.RECIPE_RECIPEFORM_URL));

        //verify
        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(anyLong());
    }

    @Test
    void deleteById() throws Exception {

        //given
        Long id = 1L;

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + id + "/delete"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())

                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));

        //verify
        Mockito.verify(recipeService, Mockito.times(1)).deleteById(anyLong());
    }
}