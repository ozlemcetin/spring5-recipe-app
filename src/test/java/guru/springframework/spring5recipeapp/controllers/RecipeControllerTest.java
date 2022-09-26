package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

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

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
    }


    @Test
    void showById() {

        //given
        Recipe recipe = null;
        Long id = 1L;
        {
            recipe = new Recipe();
            recipe.setId(id);

            Mockito.when(recipeService.findById(ArgumentMatchers.anyLong())).thenReturn(recipe);
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

            Mockito.verify(recipeService, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        }
    }
}