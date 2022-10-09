package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryCommandToCategoryTest {

    public static final Long ID_VALUE = Long.valueOf(1L);
    public static final String DESCRIPTION = "description";
    CategoryCommandToCategory commandToObj;

    @BeforeEach
    public void setUp() throws Exception {
        commandToObj = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(commandToObj.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(commandToObj.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws Exception {

        //given
        CategoryCommand command = new CategoryCommand();
        {
            command.setId(ID_VALUE);
            command.setDescription(DESCRIPTION);
        }

        //when
        Category obj = commandToObj.convert(command);

        //then
        assertNotNull(obj);
        assertEquals(ID_VALUE, obj.getId());
        assertEquals(DESCRIPTION, obj.getDescription());
    }

}