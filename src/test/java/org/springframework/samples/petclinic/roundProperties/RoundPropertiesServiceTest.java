package org.springframework.samples.petclinic.roundProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundRepository;
import org.springframework.samples.petclinic.round.RoundService;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RoundPropertiesServiceTest {

    @Mock
    private RoundPropertiesRepository roundPropertiesRepository;

    @Mock
    private RoundRepository roundRepository;

    @InjectMocks
    private RoundPropertiesService roundPropertiesService;

    @InjectMocks
    private RoundService roundService;

    @Test
    @Transactional(readOnly = true)
    public void testFindAllRoundProperties() {
        RoundProperties roundProperties1 = new RoundProperties();
        roundProperties1.setId(1);

        RoundProperties roundProperties2 = new RoundProperties();
        roundProperties2.setId(2);

        List<RoundProperties> roundPropertiesList = new ArrayList<>();
        roundPropertiesList.add(roundProperties1);
        roundPropertiesList.add(roundProperties2);

        when(roundPropertiesRepository.findAll()).thenReturn(roundPropertiesList);

        List<RoundProperties> result = roundPropertiesService.findAll();

        assertEquals(roundPropertiesList.size(), result.size());
        assertEquals(roundPropertiesList.get(0).getId(), result.get(0).getId());
        assertEquals(roundPropertiesList.get(1).getId(), result.get(1).getId());
    }

    @Test
    @Transactional
    public void testSaveRoundProperties() {
        RoundProperties roundPropertiesToSave = new RoundProperties();
        roundPropertiesToSave.setId(1);

        RoundProperties savedRoundProperties = new RoundProperties();
        savedRoundProperties.setId(1);

        when(roundPropertiesRepository.save(roundPropertiesToSave)).thenReturn(savedRoundProperties);

        RoundProperties result = roundPropertiesService.save(roundPropertiesToSave);

        assertEquals(savedRoundProperties.getId(), result.getId());
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindRoundPropertiesByPlayerAndRound() {
        Player player = new Player();
        player.setId(1);

        Round round = new Round();
        round.setId(1);

        RoundProperties roundProperties = new RoundProperties();
        roundProperties.setId(1);

        when(roundPropertiesRepository.findPropertiesByPlayerAndRound(player, round)).thenReturn(roundProperties);

        RoundProperties result = roundPropertiesService.findRoundPropertiesByPlayerAndRound(player, round);

        assertEquals(roundProperties.getId(), result.getId());
    }

    @Test
    @Transactional
    void testFindPlayersByRound() {
        Round round = new Round();
        RoundProperties rp1 = new RoundProperties();
        Player player1 = new Player();
        player1.setId(1);
        rp1.setPlayer(player1);

        RoundProperties rp2 = new RoundProperties();
        Player player2 = new Player();
        player2.setId(2);
        rp2.setPlayer(player2);

        round.setRoundProperties(List.of(rp1, rp2));

        when(roundPropertiesRepository.findPropertiesByPlayerAndRound(rp1.getPlayer(), round)).thenReturn(rp1);

        List<Player> result = roundPropertiesService.findPlayersByRound(round);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(rp1.getPlayer().getId())));
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(rp2.getPlayer().getId())));
    }

}
