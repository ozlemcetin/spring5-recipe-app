package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class UnitOfMeasureServiceIT {

    @Autowired
    UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    void listAllUnitOfMeasureCommands() {

        //When Service
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUnitOfMeasureCommands();

        //Then
        //int expectedSizeInt = 8;
        //assertEquals(expectedSizeInt, unitOfMeasureCommands.size());
    }
}