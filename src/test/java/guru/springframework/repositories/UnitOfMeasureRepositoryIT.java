package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

//@RunWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    /*
        With @Autowired Spring will do Dependency Injection on our Integration Test.
        Spring Context will start up and we will get an instance of UnitOfMeasureRepository injected.
     */

    @Autowired
    private UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DirtiesContext
    void findByDescription_Teaspoon() {

        Optional<UnitOfMeasure> optionalUnitOfMeasure = repository.findByDescription("Teaspoon");
        Assertions.assertEquals("Teaspoon", optionalUnitOfMeasure.get().getDescription());
    }

    @Test
    void findByDescription_Cup() {

        Optional<UnitOfMeasure> optionalUnitOfMeasure = repository.findByDescription("Cup");
        Assertions.assertEquals("Cup", optionalUnitOfMeasure.get().getDescription());
    }

    @Test
    void findByDescription_Dash() {

        Optional<UnitOfMeasure> optionalUnitOfMeasure = repository.findByDescription("Dash");
        Assertions.assertEquals("Dash", optionalUnitOfMeasure.get().getDescription());
    }
}