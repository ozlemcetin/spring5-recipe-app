package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        controller = new RecipeController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
    }


    @Test
    void showById() {

        //given
        Long id = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(id);

            Mockito.when(recipeService.findById(anyLong())).thenReturn(recipe);
        }

        //when
        String viewName = controller.showById(Long.toString(id), model);
        Assertions.assertEquals("recipe/show", viewName);

        //then
        {
            ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

            Mockito.verify(model, Mockito.times(1)).addAttribute(ArgumentMatchers.eq("recipe"), argumentCaptor.capture());

            Recipe recipeReturned = argumentCaptor.getValue();
            Assertions.assertNotNull(recipeReturned);
            Assertions.assertEquals(id, recipeReturned.getId());

            Mockito.verify(recipeService, Mockito.times(1)).findById(anyLong());
        }
    }

    @Test
    void newRecipe() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));
    }

    @Test
    void updateById() throws Exception {

        //given
        Long id = 2L;
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(id);

            Mockito.when(recipeService.findCommandById(anyLong())).thenReturn(command);
        }


        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //POST
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + id + "/update"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"));

    }

    @Test
    void saveOrUpdateRecipe() throws Exception {

        //given
        Long id = 2L;
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(id);

            Mockito.when(recipeService.saveRecipeCommand(any())).thenReturn(command);
        }

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //POST
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        //        .param("id", "")
                        //        .param("description", "")
                )

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())

                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/" + id + "/show"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));
    }
}