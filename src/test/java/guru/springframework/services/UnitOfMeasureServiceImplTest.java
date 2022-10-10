package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureService unitOfMeasureService;


    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);

        //unitOfMeasureService
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void listAllUnitOfMeasure() throws Exception {

        //given
        {
            Set<UnitOfMeasure> set = new HashSet<>();
            {
                UnitOfMeasure uom1 = new UnitOfMeasure();
                uom1.setId(1L);
                set.add(uom1);
            }
            {
                UnitOfMeasure uom2 = new UnitOfMeasure();
                uom2.setId(2L);
                set.add(uom2);
            }

            //when
            when(unitOfMeasureRepository.findAll()).thenReturn(set);
        }

        //then
        Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUnitOfMeasure();
        assertEquals(2, commands.size());

        //verify
        Mockito.verify(unitOfMeasureRepository, Mockito.times(1)).findAll();
    }

}