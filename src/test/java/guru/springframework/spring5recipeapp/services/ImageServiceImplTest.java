package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    ImageService imageService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);

    }

    @Test
    void saveImageFile() throws IOException {

        //Given
        Long recipeId = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);
            when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        }


         /*
        String name, @Nullable String originalFilename, @Nullable String contentType, @Nullable byte[] content
         */
        MockMultipartFile mockMultipartFile
                = new MockMultipartFile("imageFileUpload",
                "testing.txt",
                "text/plain",
                "Spring Framework Guru".getBytes());

        //When
        imageService.saveImageFile(recipeId, mockMultipartFile);


        //Verify
        {
            verify(recipeRepository, times(1)).findById(anyLong());

            //ArgumentCaptor
            ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
            verify(recipeRepository, times(1)).save(argumentCaptor.capture());

            //then
            Recipe savedRecipe = argumentCaptor.getValue();
            assertEquals(mockMultipartFile.getBytes().length, savedRecipe.getImage().length);
        }
    }
}