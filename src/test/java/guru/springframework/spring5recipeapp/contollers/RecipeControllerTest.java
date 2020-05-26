package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeControllerTest {

    RecipeController recipeController;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        //recipeController
        recipeController = new RecipeController(recipeService);

        //mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void showById() throws Exception {

        //given
        Long recipeId = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);
            when(recipeService.findById(anyLong())).thenReturn(recipe);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        //verify
        {
            verify(recipeService).findById(anyLong());
        }
    }

    @Test
    public void showByIdNotFound() throws Exception {

        //given
        Long recipeId = 1L;
        {
            when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/show"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.view().name("404NotFoundError"));

        //verify
        {
            verify(recipeService).findById(anyLong());
        }

    }

    @Test
    public void newRecipe() throws Exception {

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));

    }

    @Test
    public void updateRecipe() throws Exception {

        //given
        Long recipeId = 1L;
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(recipeId);
            when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));

        //verify
        {
            verify(recipeService).findCommandById(anyLong());
        }
    }

    @Test
    public void saveOrUpdate() throws Exception {

        //given
        Long id = 1L;
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(id);
            when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "new description"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/" + id + "/show"));

        //verify
        {
            verify(recipeService).saveRecipeCommand(any());
        }

    }

    @Test
    public void deleteRecipe() throws Exception {

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));

        //verify
        {
            verify(recipeService).deleteById(anyLong());
        }

    }


}