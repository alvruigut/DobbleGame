package org.springframework.samples.petclinic.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.gameProperties.GameProperties;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;
import org.springframework.samples.petclinic.user.UserRepository;

@SpringBootTest
@AutoConfigureTestDatabase

public class PlayerServiceTests {

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindAllPlayers() throws DataAccessException {
        Player player1 = new Player();
        player1.setFirstName("John");
        player1.setLastName("Doe");
        Player player2 = new Player();
        player2.setFirstName("Jane");
        player2.setLastName("Smith");

        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

        List<Player> result = playerService.findAll();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("Jane", result.get(1).getFirstName());
        assertEquals("Smith", result.get(1).getLastName());
    }

    @Test
    void shouldGetPlayerById() {
        Integer playerId = 1;
        Player player = new Player();
        player.setId(playerId);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        Player result = playerService.getPlayerById(playerId);

        verify(playerRepository, times(1)).findById(playerId);
        assertEquals(player, result);
    }

    @Test
    void shouldNotGetPlayerByIdNotFound(){
        Integer playerId = 0;

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            playerService.getPlayerById(playerId);
        });

        verify(playerRepository, times(1)).findById(playerId);
    }

    @Test
    void shouldFindRoundsByPlayer() {
        Player player = new Player();
        List<RoundProperties> roundPropertiesList = new ArrayList<>();
        roundPropertiesList.add(new RoundProperties());

        when(playerRepository.findRoundsByPlayer(player)).thenReturn(roundPropertiesList);

        List<Round> result = playerService.findRoundsByPlayer(player);

        verify(playerRepository,times(1)).findRoundsByPlayer(player);
        assertEquals(roundPropertiesList.size(), result.size());
    }

    @Test
    void shouldSavePlayer() {
        Player player = new Player();
        player.setId(1);

        when(playerRepository.save(player)).thenReturn(player);

        Player result = playerService.savePlayer(player);

        verify(playerRepository,times(1)).save(player);
        assertEquals(player, result);
    }

    @Test
    void shouldDeletePlayerById() {
        Player player = new Player();
        player.setId(1);
        playerRepository.save(player);

        playerRepository.deleteById(player.getId());

        Player deletedPlayer = playerRepository.findById(player.getId()).orElse(null);
        assertNull(deletedPlayer);
    }

    @Test
    void shouldDeletePlayer() throws Exception  {
        Player player = new Player();
        player.setId(1);

        playerService.deletePlayer(player);

        verify(playerRepository, times(1)).delete(player);
    }

    
}