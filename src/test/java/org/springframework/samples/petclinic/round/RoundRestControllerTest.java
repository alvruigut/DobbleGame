package org.springframework.samples.petclinic.round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoundRestControllerTest {

    @Mock
    private RoundService roundService;

    @InjectMocks
    private RoundRestController roundController;

    @Test
    void testFindAllRounds() {
        List<Round> roundList = new ArrayList<>();
        roundList.add(new Round());
        roundList.add(new Round());
        when(roundService.findAllRounds()).thenReturn(roundList);

        List<Round> result = roundController.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }





}
