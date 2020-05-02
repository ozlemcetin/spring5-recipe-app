package guru.springframework.spring5recipeapp.repositories;

import guru.springframework.spring5recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByDescription() {

        String desc = "American";
        Optional<Category> category = categoryRepository.findByDescription(desc);
        assertEquals(desc, category.get().getDescription());
    }

    @Test
    public void save() {

        Category category = null;
        Category savedCategory = null;
        {
            category = new Category();
            category.setDescription("Turkish");

            savedCategory = categoryRepository.save(category);
        }

        assertNotNull(savedCategory);
        assertEquals(savedCategory.getDescription(), category.getDescription());
    }

    @Test
    public void count() {

        long count = categoryRepository.count();
        assertNotEquals(0, count);
    }

}