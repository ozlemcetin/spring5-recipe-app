package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

class IndexControllerTest {

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;
    private IndexController controller;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        controller = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    void getIndexPage() {

        //given
        Set<Recipe> set = null;
        {
            set = new HashSet<>();
            {
                Recipe recipe = new Recipe();
                recipe.setId(1L);
                set.add(recipe);
            }
            {
                Recipe recipe = new Recipe();
                recipe.setId(2L);
                set.add(recipe);
            }

            //when
            Mockito.when(recipeService.getRecipes()).thenReturn(set);
        }

        //then
        String viewName = controller.getIndexPage(model);
        Assertions.assertEquals("index", viewName);

        //verify
        {
            ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
            Mockito.verify(model, Mockito.times(1))
                    //The fist value is equal to "recipes", the second value is going to be any set
                    //.addAttribute(ArgumentMatchers.eq("recipes"), ArgumentMatchers.anySet());
                    .addAttribute(ArgumentMatchers.eq("recipes"), argumentCaptor.capture());

            Set<Recipe> capturedSet = argumentCaptor.getValue();
            Assertions.assertEquals(set.size(), capturedSet.size());

            Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        }
    }

}