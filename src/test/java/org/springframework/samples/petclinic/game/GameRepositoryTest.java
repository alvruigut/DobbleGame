package org.springframework.samples.petclinic.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.gameProperties.GameProperties;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@DataJpaTest
@SpringJUnitConfig
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;
    private List<Game> games;
    private int numberOfGames;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private GameRepository mockGameRepository;

    @BeforeEach
    void setUp() {
        this.games = this.gameRepository.findAll();
        this.numberOfGames = this.games.size();
    }

    @Test
    void testFindAll() {
        Game game1 = new Game();
        Game game2 = new Game();
        List<Game> games = Arrays.asList(game1, game2);

        when(mockGameRepository.findAll()).thenReturn(games);

        List<Game> result = gameRepository.findAll();

        assertEquals(games.size(), result.size());
    }

    @Test
    void shouldNotFindInvalidId() {
        int gameId = Integer.MIN_VALUE;
        Optional<Game> expectedGame = Optional.empty();
        Optional<Game> actualGame = this.gameRepository.findById(gameId);
        assertEquals(expectedGame, actualGame, "It should not find an invalid game id");
    }

    @Test
    void testFindAllByState() {
        GameState state = GameState.ONGOING;
        Game game1 = new Game();
        game1.setState(state);

        Game game2 = new Game();
        game2.setState(state);

        List<Game> games = Arrays.asList(game1, game2);

        when(mockGameRepository.findAllByState(state)).thenReturn(games);

        List<Game> result = gameRepository.findAllByState(state);

        verify(mockGameRepository, times(1)).findAllByState(state);

        assertEquals(games, result);
    }

    @Test
    void testFindRoundsByGameId() {
        Integer gameId = 1;
        Round round1 = new Round();
        Round round2 = new Round();
        List<Round> rounds = Arrays.asList(round1, round2);

        when(mockGameRepository.findRoundsByGameId(gameId)).thenReturn(rounds);

        List<Round> result = gameRepository.findRoundsByGameId(gameId);

        verify(mockGameRepository, times(1)).findRoundsByGameId(gameId);

        assertEquals(rounds, result);
    }

    @Test
    void testFindByUsername() {
        String username = "nombreDeUsuario";
        User user = new User();
        user.setUsername(username);

        Player player = new Player();
        player.setUser(user);

        GameProperties gameProperties1 = new GameProperties();
        gameProperties1.setPlayer(player);

        GameProperties gameProperties2 = new GameProperties();
        gameProperties2.setPlayer(player);

        Game game1 = new Game();
        game1.setGameProperties(Arrays.asList(gameProperties1));

        Game game2 = new Game();
        game2.setGameProperties(Arrays.asList(gameProperties2));

        List<Game> games = Arrays.asList(game1, game2);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(mockGameRepository.findByUserName(anyString())).thenReturn(games);

        List<Game> result = gameRepository.findByUserName(username);

        verify(mockGameRepository, times(1)).findByUserName(username);

        assertEquals(games, result);

    }

    @Test
    void testFindGameByCode() {
        String gameCode = "codigoDelJuego";
        Game game = new Game();
        game.setGameCode(gameCode);

        when(mockGameRepository.findGameByCode(gameCode)).thenReturn(game);

        Game result = gameRepository.findGameByCode(gameCode);

        verify(gameRepository, times(1)).findGameByCode(gameCode);

        assertEquals(game, result);
    }

    @Test
    void testFindPlayersByGame() {
        Game game = new Game();
        Player player1 = new Player();
        Player player2 = new Player();
        List<Player> players = Arrays.asList(player1, player2);

        when(gameRepository.findPlayersByGame(any())).thenReturn(players);

        List<Player> result = gameRepository.findPlayersByGame(game);

        verify(gameRepository, times(1)).findPlayersByGame(game);

        assertEquals(players, result);
    }

}

