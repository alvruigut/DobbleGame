package org.springframework.samples.petclinic.round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.roundProperties.RoundProperties;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@DataJpaTest
@SpringJUnitConfig
public class RoundRepositoryTest {

    @Mock
    private Round round;

    @Autowired
    private RoundRepository roundRepository;

    @MockBean
    private RoundRepository mockRoundRepository;

    @Test
    void testFindAllRounds() {
        Round round1 = new Round();
        Round round2 = new Round();
        List<Round> rounds = Arrays.asList(round1, round2);

        when(mockRoundRepository.findAllRounds()).thenReturn(rounds);
        
        List<Round> result = roundRepository.findAllRounds();

        assertEquals(rounds.size(), result.size());

        verify(mockRoundRepository).findAllRounds();
    }

    @Test
    void testSaveRound() {
    
        Round roundToSave = new Round();
        Round savedRound = new Round();

        when(roundRepository.save(roundToSave)).thenReturn(savedRound);

        Round result = roundRepository.save(roundToSave);

        assertEquals(savedRound, result);

        verify(roundRepository).save(roundToSave);
    }

    @Test
    void testFindPlayersByGame() {
        Round round = new Round();
        RoundProperties rp1 = new RoundProperties();
        RoundProperties rp2 = new RoundProperties();
        List<RoundProperties> roundPropertiesList = Arrays.asList(rp1, rp2);

        when(roundRepository.findPlayersByGame(round)).thenReturn(roundPropertiesList);

        List<RoundProperties> result = roundRepository.findPlayersByGame(round);

        assertEquals(roundPropertiesList.size(), result.size());

        verify(roundRepository).findPlayersByGame(round);
    }

    @Test
    void testFindGameByRoundId() {
        Round round = new Round();
        Game game1 = new Game();

        when(roundRepository.findGameByRoundId(round)).thenReturn(game1);

        Game resultGame = roundRepository.findGameByRoundId(round);

        assertEquals(game1, resultGame);

        verify(roundRepository).findGameByRoundId(round);
    }
    
}
