package guru.springframework.spring5recipeapp.converters;


import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by jt on 6/21/17.
 */
public class CategoryToCategoryCommandTest {

    public static final Long ID_VALUE = Long.valueOf(1L);
    public static final String DESCRIPTION = "description";

    CategoryToCategoryCommand objToCommand;

    @BeforeEach
    public void setUp() throws Exception {
        objToCommand = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(objToCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(objToCommand.convert(new Category()));
    }

    @Test
    public void convert() throws Exception {

        //given
        Category obj = new Category();
        {
            obj.setId(ID_VALUE);
            obj.setDescription(DESCRIPTION);
        }

        //when
        CategoryCommand command = objToCommand.convert(obj);

        //then
        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }

}