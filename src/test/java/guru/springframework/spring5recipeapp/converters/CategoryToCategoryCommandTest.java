package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand toCategoryCommand;

    @Before
    public void setUp() throws Exception {
        toCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    public void convert_null() {
        assertNull(toCategoryCommand.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(toCategoryCommand.convert(new Category()));
    }

    @Test
    public void convert() {

        Long id = 1L;
        String desc = "Category Description";
        CategoryCommand categoryCommand = null;
        {
            Category category = new Category();
            category.setId(id);
            category.setDescription(desc);

            categoryCommand = toCategoryCommand.convert(category);
        }

        assertNotNull(categoryCommand);
        assertEquals(id, categoryCommand.getId());
        assertEquals(desc, categoryCommand.getDescription());
    }
}