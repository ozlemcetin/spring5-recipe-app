package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.ImageService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;


    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String showImageUploadForm(@PathVariable String recipeId, Model model) {

        //Find the recipe
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipeCommand", recipeCommand);

        return "/recipe/imageUploadForm";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImageUploadFormPost(@PathVariable String recipeId,
                                            @RequestParam("imageFileUpload") MultipartFile file) {

        //Save Image
        imageService.saveImageFile(Long.valueOf(recipeId), file);

        return "redirect:/recipe/" + recipeId + "/update";
    }

    @GetMapping("/recipe/{recipeId}/image/show")
    public void renderImageFromDB(@PathVariable String recipeId,
                                HttpServletResponse httpServletResponse) {

        //Find the recipe
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        if (recipeCommand != null && recipeCommand.getImage() != null) {

            Byte[] bytesOfDB = recipeCommand.getImage();
            byte[] byteWrapper = new byte[bytesOfDB.length];
            for (int i = 0; i < bytesOfDB.length; i++) {
                byteWrapper[i] = bytesOfDB[i];
            }

            try {

                httpServletResponse.setContentType("image/jpeg");
                InputStream inputStream = new ByteArrayInputStream(byteWrapper);
                IOUtils.copy(inputStream, httpServletResponse.getOutputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
