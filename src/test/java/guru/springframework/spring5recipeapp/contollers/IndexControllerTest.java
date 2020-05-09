package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;


    @Before
    public void setUp() throws Exception {
        /*
        Give me a mock RecipeService
         */
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));

    }

    @Test
    public void getIndexPage() {

        {
            Set<Recipe> recipeSet = new HashSet<>();

            Recipe r1 = new Recipe();
            r1.setId(1L);
            recipeSet.add(r1);

            Recipe r2 = new Recipe();
            r2.setId(2L);
            recipeSet.add(r2);

            when(recipeService.getRecipes()).thenReturn(recipeSet);
        }


        String viewNameStr = "index";
        assertEquals(viewNameStr, indexController.getIndexPage(model));

      /*
        Verify that recipeService getRecipes() is called once and only once
       */
        {
            verify(recipeService, times(1)).getRecipes();

            ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
            verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

            //then
            Set<Recipe> setInController = argumentCaptor.getValue();
            assertEquals(2, setInController.size());
        }

    }
}