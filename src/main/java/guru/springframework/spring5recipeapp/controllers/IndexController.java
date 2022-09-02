package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.repositories.CategoryRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {

        System.out.println("Category Id : " + categoryRepository.findByDescription("American").get().getId());
        System.out.println("Measure Id: " + unitOfMeasureRepository.findByDescription("Teaspoon").get().getId());

        return "index";
    }
}
