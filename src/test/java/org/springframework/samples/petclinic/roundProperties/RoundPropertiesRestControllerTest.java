package org.springframework.samples.petclinic.roundProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundService;

@ExtendWith(MockitoExtension.class)
public class RoundPropertiesRestControllerTest {

    @Mock
    private RoundPropertiesService roundPropertiesService;

    @Mock
    private PlayerService playerService;

    @Mock
    private RoundService roundService;

    @InjectMocks
    private RoundPropertiesController roundPropertiesController;

    @Test
    void testGetAllRoundProperties() {
        RoundProperties roundProperties1 = new RoundProperties();
        roundProperties1.setId(1);

        RoundProperties roundProperties2 = new RoundProperties();
        roundProperties2.setId(2);

        List<RoundProperties> roundPropertiesList = new ArrayList<>();
        roundPropertiesList.add(roundProperties1);
        roundPropertiesList.add(roundProperties2);
        
        when(roundPropertiesService.findAll()).thenReturn(roundPropertiesList);

        ResponseEntity<List<RoundProperties>> responseEntity = roundPropertiesController.getAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(roundPropertiesList, responseEntity.getBody());
    }

    @Test
    void testFindPlayersByRound() {
        Round round = new Round();
        round.setId(1);

        Player player1 = new Player();
        player1.setId(1);
        
        Player player2 = new Player();
        player2.setId(2);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        when(roundPropertiesService.findPlayersByRound(round)).thenReturn(players);

        List<Player> result = roundPropertiesController.findPlayersByRound(round);

        assertEquals(players, result);
    }

    @Test
    void testCreate() {
        RoundProperties requestRoundProperties = new RoundProperties();
        requestRoundProperties.setPlayer(new Player());
        requestRoundProperties.setRound(new Round());

        when(roundPropertiesService.save(any(RoundProperties.class))).thenReturn(requestRoundProperties);

        ResponseEntity<RoundProperties> responseEntity = roundPropertiesController.create(requestRoundProperties);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testFindRoundPropertiesByRoundAndPlayer() {
        Round round = new Round();
        round.setId(1);

        Player player = new Player();
        player.setId(1);

        when(roundService.getRoundById(1)).thenReturn(round);

        when(playerService.getPlayerById(1)).thenReturn(player);

        RoundProperties roundProperties = new RoundProperties();
        roundProperties.setId(1);

        when(roundPropertiesService.findRoundPropertiesByPlayerAndRound(player, round)).thenReturn(roundProperties);

        ResponseEntity<RoundProperties> responseEntity = roundPropertiesController.findRpByRoundAndPlayer(1, 1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(roundProperties, responseEntity.getBody());
    }

    @Test
    void testFindRoundPropertiesByRoundAndPlayerButRoundNotFound() {
        when(roundService.getRoundById(2)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            roundPropertiesController.findRpByRoundAndPlayer(2, 1);
        });
    }

    @Test
    void testFindRoundPropertiesByRoundAndPlayerButPlayerNotFound() {
        when(playerService.getPlayerById(2)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            roundPropertiesController.findRpByRoundAndPlayer(1, 2);
        });
    }

    @Test
    void testFindRoundPropertiesByRoundAndPlayerButRoundPropertiesNotFound() {
        Round round = new Round();
        round.setId(1);

        when(roundService.getRoundById(1)).thenReturn(round);

        Player player = new Player();
        player.setId(1);

        when(playerService.getPlayerById(1)).thenReturn(player);

        when(roundPropertiesService.findRoundPropertiesByPlayerAndRound(player, round)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            roundPropertiesController.findRpByRoundAndPlayer(1, 1);
        });
    }
    
}
