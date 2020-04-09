package guru.springframework.spring5recipeapp.contollers;

import guru.springframework.spring5recipeapp.domain.Category;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.CategoryRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by jt on 6/1/17.
 */
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

        Optional<Category> optionalCategory =
                categoryRepository.findByDescription("American");
        optionalCategory.ifPresent(e -> System.out.println("Category Id: " + e.getId()));

        Optional<UnitOfMeasure> optionalUnitOfMeasure =
                unitOfMeasureRepository.findByDescription("Pinch");
        optionalUnitOfMeasure.ifPresent(e -> System.out.println("Measure Id: " + e.getId()));

        return "index";
    }
}
