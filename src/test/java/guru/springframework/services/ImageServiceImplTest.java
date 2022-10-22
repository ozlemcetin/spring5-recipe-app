package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;


public class ImageServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    private ImageService imageService;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws Exception {

        //given
        Long recipeId = 1L;
        {
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);

            //when
            Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        }

        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());

        //then
        imageService.saveImageFile(recipeId, multipartFile);

        //verify
        {
            ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
            Mockito.verify(recipeRepository, times(1)).save(argumentCaptor.capture());

            Recipe capturedRecipe = argumentCaptor.getValue();
            assertEquals(multipartFile.getBytes().length, capturedRecipe.getImage().length);
        }
    }

}