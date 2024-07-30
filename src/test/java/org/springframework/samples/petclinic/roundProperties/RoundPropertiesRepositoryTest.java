package org.springframework.samples.petclinic.roundProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@DataJpaTest
@SpringJUnitConfig
public class RoundPropertiesRepositoryTest {

    @Autowired
    private RoundPropertiesRepository roundPropertiesRepository;

    @MockBean
    private RoundPropertiesRepository mockRoundPropertiesRepository;

    @Test
    void testFindAll() {
        RoundProperties roundProperties1 = new RoundProperties();
        RoundProperties roundProperties2 = new RoundProperties();
        List<RoundProperties> roundPropertiesList = Arrays.asList(roundProperties1, roundProperties2);

        when(mockRoundPropertiesRepository.findAll()).thenReturn(roundPropertiesList);

        List<RoundProperties> result = roundPropertiesRepository.findAll();

        assertEquals(roundPropertiesList.size(), result.size());
    }

    @Test
    void testFindPropertiesByPlayerAndRound() {
        Player player = new Player();
        Round round = new Round();
        RoundProperties roundProperties = new RoundProperties();
        
        when(roundPropertiesRepository.findPropertiesByPlayerAndRound(player, round)).thenReturn(roundProperties);

        RoundProperties result = mockRoundPropertiesRepository.findPropertiesByPlayerAndRound(player, round);

        assertNotNull(result);
    }

    @Test
    void testFindPlayerPoints() {
        Player player = new Player();
        Round round = new Round();
        
        Integer result = roundPropertiesRepository.findPlayerPoints(player, round);

        assertNotNull(result);
    }
    
}
