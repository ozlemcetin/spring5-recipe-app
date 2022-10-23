package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {


    @Mock
    private ImageService imageService;
    @Mock
    private RecipeService recipeService;

    private ImageController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
        controller = new ImageController(imageService, recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void showUploadForm() throws Exception {

        //given
        Long recipeId = 1L;
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(recipeId);

            when(recipeService.findCommandById(anyLong())).thenReturn(command);
        }

        //then
        mockMvc.perform(get("/recipe/" + recipeId + "/image"))

                .andExpect(status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("recipe/imageuploadform"))

                .andExpect(model().attributeExists("recipeCommand"));

        //verify
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void handleImagePost() throws Exception {

        //given
        Long recipeId = 1L;

        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());

        //then
        mockMvc.perform(multipart("/recipe/1/image")

                        .file(multipartFile))

                .andExpect(status().is3xxRedirection())

                .andExpect(view().name("redirect:/recipe/" + recipeId + "/show"))

                .andExpect(header().string("Location", "/recipe/" + recipeId + "/show"));

        //verify
        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void renderImageFromDB() throws Exception {

        //given
        Long recipeId = 1L;
        String fileStr = "fake image text";
        {
            RecipeCommand command = new RecipeCommand();
            command.setId(recipeId);

            //setImage
            {
                Byte[] bytesBoxed = new Byte[fileStr.getBytes().length];
                {
                    int i = 0;
                    for (byte primByte : fileStr.getBytes()) {
                        bytesBoxed[i++] = primByte;
                    }
                }
                command.setImage(bytesBoxed);
            }

            //when
            when(recipeService.findCommandById(anyLong())).thenReturn(command);
        }


        //then
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/" + recipeId + "/recipeimage"))

                .andExpect(status().isOk())

                .andReturn().getResponse();

        byte[] reponseBytes = response.getContentAsByteArray();
        assertEquals(fileStr.getBytes().length, reponseBytes.length);

        //verify
        verify(recipeService, times(1)).findCommandById(anyLong());
    }
}