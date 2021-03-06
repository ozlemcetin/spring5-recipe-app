package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.ImageService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

class ImageControllerTest {

    ImageController imageController;

    @Mock
    RecipeService recipeService;

    @Mock
    ImageService imageService;


    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        //imageController
        imageController = new ImageController(recipeService, imageService);

        //mockMvc
        mockMvc = MockMvcBuilders
                .standaloneSetup(imageController)
                .setControllerAdvice(new ExceptionHandlerControllerAdvice())
                .build();
    }

    @Test
    void showImageUploadForm() throws Exception {

        //given
        Long recipeId = 1L;
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(recipeId);
            when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        }

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/image"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/recipe/imageUploadForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipeCommand"));

        //verify
        {
            verify(recipeService).findCommandById(anyLong());
        }
    }

    @Test
    void handleImageUploadFormPost() throws Exception {

        //given
        Long recipeId = 1L;

        /*
        String name, @Nullable String originalFilename, @Nullable String contentType, @Nullable byte[] content
         */
        MockMultipartFile mockMultipartFile
                = new MockMultipartFile("imageFileUpload",
                "testing.txt",
                "text/plain",
                "Spring Framework Guru".getBytes());

        //when
        mockMvc.perform(multipart("/recipe/" + recipeId + "/image").file(mockMultipartFile))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/recipe/" + recipeId + "/update"));

        //verify
        {
            verify(imageService).saveImageFile(any(), any());
        }
    }

    @Test
    void renderImageFromDB() throws Exception {

        //given
        Long recipeId = 1L;
        String imageStr = "Spring Framework Guru Image Text";
        {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(recipeId);

            Byte[] byteWrappper = null;
            {

                byteWrappper = new Byte[imageStr.getBytes().length];

                int i = 0;
                for (byte b : imageStr.getBytes()) {
                    byteWrappper[i++] = b;
                }
            }
            recipeCommand.setImage(byteWrappper);

            when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        }

        //when
        MockHttpServletResponse response
                = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/image/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        byte[] content =
                response.getContentAsByteArray();
        assertEquals(imageStr.getBytes().length, content.length);

        //verify
        {
            verify(recipeService).findCommandById(anyLong());
        }

    }

    @Test
    public void renderImageFromDBBadRequest() throws Exception {

        //given
        String recipeId = "asfd";

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/" + recipeId + "/image/show"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("error"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("title"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("exception"));

    }

}