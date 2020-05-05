package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    /*
    Constructor
     */
    public UnitOfMeasureServiceImplTest() {
        //init converters
        this.toUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,
                toUnitOfMeasureCommand);
    }

    @Test
    void listAllUnitOfMeasureCommands() {

        //Given
        {
            Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();

            UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
            unitOfMeasure1.setId(1L);
            unitOfMeasureSet.add(unitOfMeasure1);

            UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
            unitOfMeasure2.setId(2L);
            unitOfMeasureSet.add(unitOfMeasure2);

            when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureSet);
        }

        //When Service
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUnitOfMeasureCommands();

        //Then
        int expectedSizeInt = 2;
        assertEquals(expectedSizeInt, unitOfMeasureCommands.size());

        //Verify
        {
            verify(unitOfMeasureRepository).findAll();
        }

    }
}