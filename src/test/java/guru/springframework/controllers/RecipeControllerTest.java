package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import guru.springframework.commands.RecipeCommand;
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
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void showById() throws Exception {

        //given
        Long id = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(id);

            Mockito.when(recipeService.findById(anyLong())).thenReturn(recipe);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + id + "/show"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/show"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        //then
        {
            Mockito.verify(recipeService, Mockito.times(1)).findById(anyLong());
        }
    }

    @Test
    void newRecipe() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));
    }

    @Test
    void updateById() throws Exception {

        //given
        Long id = 1L;
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(id);

            Mockito.when(recipeService.findCommandById(anyLong())).thenReturn(command);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + id + "/update"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"));


        //then
        {
            Mockito.verify(recipeService, Mockito.times(1)).findCommandById(anyLong());
        }

    }

    @Test
    void saveOrUpdateRecipe() throws Exception {

        //given
        Long id = 1L;
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(id);

            Mockito.when(recipeService.saveRecipeCommand(any())).thenReturn(command);
        }

        //POST
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        //        .param("id", "")
                        //        .param("description", "")
                )

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())

                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/" + id + "/show"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));

        //then
        {
            Mockito.verify(recipeService, Mockito.times(1)).saveRecipeCommand(any());
        }
    }


    @Test
    void deleteById() throws Exception {

        //given
        Long id = 1L;

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + id + "/delete"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())

                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));

        //then
        {
            Mockito.verify(recipeService, Mockito.times(1)).deleteById(anyLong());
        }
    }


}