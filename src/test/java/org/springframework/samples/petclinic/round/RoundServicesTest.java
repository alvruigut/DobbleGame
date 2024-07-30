package org.springframework.samples.petclinic.round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.card.CardRepository;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;
import org.springframework.samples.petclinic.roundProperties.RoundPropertiesRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase
public class RoundServicesTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private RoundPropertiesRepository roundPropertiesRepository;

    @Mock
    private RoundRepository roundRepository;

    @InjectMocks
    private RoundService roundService;

    @Test
    @Transactional
    public void testFindAllRounds() {
        Round round1 = new Round();
        round1.setId(1);

        Round round2 = new Round();
        round2.setId(2);

        List<Round> rounds = new ArrayList<>();
        rounds.add(round1);
        rounds.add(round2);

        when(roundRepository.findAllRounds()).thenReturn(rounds);

        List<Round> result = roundService.findAllRounds();
        assertEquals(rounds.size(), result.size());
        assertEquals(rounds.get(0).getId(), result.get(0).getId());
        assertEquals(rounds.get(1).getId(), result.get(1).getId());
    }

    @Test
    @Transactional
    public void testSaveRound() {
        Round roundToSave = new Round();
        roundToSave.setId(1);
        roundToSave.setIsOnGoing(false);
        roundToSave.setMinigame(Minigame.PATATACALIENTE);

        Round savedRound = new Round();
        savedRound.setId(1);
        savedRound.setIsOnGoing(false);
        savedRound.setMinigame(Minigame.PATATACALIENTE);

        when(roundRepository.save(roundToSave)).thenReturn(savedRound);

        Round result = roundService.save(roundToSave);

        assertEquals(savedRound.getId(), result.getId());
        assertEquals(savedRound.getIsOnGoing(), result.getIsOnGoing());
        assertEquals(savedRound.getMinigame(), result.getMinigame());

    }

    @Test
    @Transactional(readOnly = true)
    public void testGetRoundById() {
        int roundId = 1;
        Round round = new Round();
        round.setId(roundId);
        round.setIsOnGoing(false);
        round.setMinigame(Minigame.PATATACALIENTE);

        when(roundRepository.findById(roundId)).thenReturn(Optional.of(round));

        Round result = roundService.getRoundById(roundId);

        assertEquals(round.getId(), result.getId());
        assertEquals(round.getIsOnGoing(), result.getIsOnGoing());
        assertEquals(round.getMinigame(), result.getMinigame());

        int nonExistingRoundId = 100;
        when(roundRepository.findById(nonExistingRoundId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> roundService.getRoundById(nonExistingRoundId));
    }

    @Test
    public void testGetPlayersByRound() {
        Integer roundId = 1;
        Round round = new Round();
        round.setId(roundId);

        Player player1 = new Player();
        player1.setId(1);
        Player player2 = new Player();
        player2.setId(2);

        List<RoundProperties> roundPropertiesList = new ArrayList<>();
        RoundProperties roundProperties1 = new RoundProperties();
        roundProperties1.setRound(round);
        roundProperties1.setPlayer(player1);

        RoundProperties roundProperties2 = new RoundProperties();
        roundProperties2.setRound(round);
        roundProperties2.setPlayer(player2);

        roundPropertiesList.add(roundProperties1);
        roundPropertiesList.add(roundProperties2);

        when(roundRepository.findById(roundId)).thenReturn(Optional.of(round));
        when(roundRepository.findPlayersByGame(round)).thenReturn(roundPropertiesList);

        List<Player> result = roundService.getPlayersByRound(roundId);

        assertEquals(2, result.size());
        assertEquals(player1.getId(), result.get(0).getId());
        assertEquals(player2.getId(), result.get(1).getId());
    }

    @Test
    public void testGetGame() {
        Integer roundId = 1;
        Round round = new Round();
        round.setId(roundId);

        Game game = new Game();
        game.setId(1);

        when(roundRepository.findById(roundId)).thenReturn(Optional.of(round));
        when(roundRepository.findGameByRoundId(round)).thenReturn(game);

        Game result = roundService.getGame(roundId);

        assertEquals(game.getId(), result.getId());
    }

    
}
