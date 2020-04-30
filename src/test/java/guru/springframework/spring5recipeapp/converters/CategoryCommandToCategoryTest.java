package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryCommandToCategoryTest {

    CategoryCommandToCategory commandToCategory;


    @Before
    public void setUp() throws Exception {
        commandToCategory = new CategoryCommandToCategory();
    }

    @Test
    public void convert_null() {
        assertNull(commandToCategory.convert(null));
    }

    @Test
    public void convert_notNull() {
        assertNotNull(commandToCategory.convert(new CategoryCommand()));
    }

    @Test
    public void convert() {

        Long id = 1L;
        String desc = "Category Description";
        Category category = null;
        {
            CategoryCommand categoryCommand = new CategoryCommand();
            categoryCommand.setId(id);
            categoryCommand.setDescription(desc);

            category = commandToCategory.convert(categoryCommand);
        }

        assertNotNull(category);
        assertEquals(id, category.getId());
        assertEquals(desc, category.getDescription());
    }
}