package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {

        log.debug("Received an image file to save.");

        //Find the recipe
        Optional<Recipe> recipeOptional
                = recipeRepository.findById(Long.valueOf(recipeId));

        if (!recipeOptional.isPresent()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Byte[] byteWrapper = null;
        try {
            byte[] bytesOfFile = file.getBytes();
            byteWrapper = new Byte[bytesOfFile.length];
            for (int i = 0; i < bytesOfFile.length; i++) {
                byteWrapper[i] = bytesOfFile[i];
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        recipe.setImage(byteWrapper);

        //Save Recipe
        recipeRepository.save(recipe);
    }
}
