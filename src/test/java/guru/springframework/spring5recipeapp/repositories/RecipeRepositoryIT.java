package guru.springframework.spring5recipeapp.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest

public class RecipeRepositoryIT {

    @Autowired
    RecipeRepository recipeRepository;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void count() {

        /*
        @DataJpaTest uses lighter weight slice, it cannot find our Recipe Servic Imp. Use @SpringBootTest Instead.
         */
        long count = recipeRepository.count();
        assertEquals(0, count);
    }


}