package org.springframework.samples.petclinic.gameProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.roundProperties.RoundPropertiesRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class GamePropServiceTest {

    @Mock
    private GamePropertiesRepository gamePropertiesRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private RoundPropertiesRepository roundPropertiesRepository;

    @InjectMocks
    private GamePropertiesService gamePropertiesService;

    @Test
    @Transactional(readOnly = true)
    public void testFindAllGameProperties() {
        GameProperties gameProperties1 = new GameProperties();
        gameProperties1.setId(1);

        GameProperties gameProperties2 = new GameProperties();
        gameProperties2.setId(2);

        List<GameProperties> gameProperties = new ArrayList<>();
        gameProperties.add(gameProperties1);
        gameProperties.add(gameProperties2);

        when(gamePropertiesRepository.findAll()).thenReturn(gameProperties);

        List<GameProperties> result = gamePropertiesService.findAll();

        assertEquals(gameProperties.size(), result.size());
        assertEquals(gameProperties.get(0).getId(), result.get(0).getId());
        assertEquals(gameProperties.get(1).getId(), result.get(1).getId());

    }

    @Test
    @Transactional(readOnly = true)
    public void testFindGamePropertiesByGame() {
        Game game = new Game();
        game.setId(1);

        GameProperties gameProperties = new GameProperties();
        gameProperties.setId(1);

        when(gamePropertiesRepository.findGamePropertiesByGame(game)).thenReturn(gameProperties);

        GameProperties result = gamePropertiesService.findGamePropertiesByGame(game);

        assertEquals(gameProperties.getId(), result.getId());
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindGamePropertiesByPlayer() {
        Player player = new Player();
        player.setId(1);

        GameProperties gameProperties = new GameProperties();
        gameProperties.setId(1);

        when(gamePropertiesRepository.findGamePropertiesByPlayer(player)).thenReturn(gameProperties);

        GameProperties result = gamePropertiesService.findGamePropertiesByPlayer(player);

        assertEquals(gameProperties.getId(), result.getId());
        
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindIfIsCreator() {
        Integer gameId = 1;
        Integer playerId = 1;

        Game game = new Game();
        game.setId(gameId);

        Player player = new Player();
        player.setId(playerId);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(gamePropertiesRepository.findIfIsCreator(game, player)).thenReturn(true);

        Boolean result = gamePropertiesService.findIfIsCreator(gameId, playerId);

        assertEquals(true, result);
    }

    @Test
    @Transactional
    public void testSaveGameProperties() {
        GameProperties gamePropertiesToSave = new GameProperties();
        gamePropertiesToSave.setId(1);

        when(gamePropertiesRepository.save(gamePropertiesToSave)).thenReturn(gamePropertiesToSave);

        GameProperties result = gamePropertiesService.save(gamePropertiesToSave);

        assertNotNull(result.getId());
        assertEquals(gamePropertiesToSave.getId(), result.getId());
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindGPByGameAndPlayer() {
        Integer gameId = 1;
        Integer playerId = 1;

        Game game = new Game();
        game.setId(gameId);

        Player player = new Player();
        player.setId(playerId);

        GameProperties gameProperties = new GameProperties();
        gameProperties.setId(1);

        when(gameRepository.findById(playerId)).thenReturn(Optional.of(game));
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(gamePropertiesRepository.findGPByGameAndPlayer(game, player)).thenReturn(gameProperties);

        GameProperties result = gamePropertiesService.findByGameAndPlayer(gameId, playerId);

        assertEquals(gameProperties.getId(), result.getId());
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetAllPointsOfPlayerInGame() {
        Integer gameId = 1;
        Integer playerId = 1;

        Player player = new Player();
        player.setId(playerId);

        Game game = new Game();
        game.setId(gameId);

        Round round1 = new Round();
        round1.setId(1);

        Round round2 = new Round();
        round2.setId(2);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(gameRepository.findRoundsByGameId(gameId)).thenReturn(Arrays.asList(round1, round2));
        when(roundPropertiesRepository.findPlayerPoints(player, round1)).thenReturn(10);
        when(roundPropertiesRepository.findPlayerPoints(player, round2)).thenReturn(20);

        Integer result = gamePropertiesService.getAllPointsOfPlayerInGame(gameId, playerId);

        assertEquals(30, result);
    }

    
}
