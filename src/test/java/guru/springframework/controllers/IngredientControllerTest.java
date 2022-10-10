package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.anyLong;

class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    private IngredientController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        //controller
        controller = new IngredientController(recipeService);

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

            Mockito.when(recipeService.findCommandById(anyLong())).thenReturn(command);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + id + "/ingredients"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));

        //then
        {
            Mockito.verify(recipeService, Mockito.times(1)).findCommandById(anyLong());
        }
    }
}