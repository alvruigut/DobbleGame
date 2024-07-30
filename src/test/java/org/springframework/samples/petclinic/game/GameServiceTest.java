package org.springframework.samples.petclinic.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.gameProperties.GamePropertiesRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;

@SpringBootTest
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GamePropertiesRepository gamePropertiesRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testFindAll() {
        List<Game> expectedGames = Arrays.asList(new Game());
        when(gameRepository.findAll()).thenReturn(expectedGames);

        List<Game> actualGames = gameService.findAll();
        verify(gameRepository).findAll();

        assertEquals(expectedGames, actualGames);
    }

    @Test
    public void testFindRoundByGameId() {
        List<Round> expectedRounds = Arrays.asList(new Round());
        when(gameRepository.findRoundsByGameId(anyInt())).thenReturn(expectedRounds);

        List<Round> actualRounds = gameService.findRoundsById(1);

        verify(gameRepository).findRoundsByGameId(1);
        assertEquals(expectedRounds, actualRounds);
    }

    @Test
    public void testFindByUsername() {
        String username = "testUser";
        List<Game> expectedGames = Arrays.asList(new Game());
        when(gameRepository.findByUserName(username)).thenReturn(expectedGames);

        List<Game> actualGames = gameService.findByUsername(username);
        verify(gameRepository).findByUserName(username);
        assertEquals(expectedGames, actualGames);
    }

    @Test
    public void testFindPlayersByGame() {
        Game game = new Game();
        List<Player> expectedPlayers = Arrays.asList(new Player());
        when(gameRepository.findPlayersByGame(game)).thenReturn(expectedPlayers);

        List<Player> actualPlayers = gameService.findPlayersByGame(game);
        verify(gameRepository).findPlayersByGame(game);

        assertEquals(expectedPlayers, actualPlayers);
    }

    @Test
    public void testFindGameById(){
        int gameId = 1;
        Game game = new Game();
        game.setId(gameId);
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        Game foundGame = gameService.findById(gameId);
        verify(gameRepository).findById(gameId);

        assertNotNull(foundGame);
        assertEquals(gameId, foundGame.getId());
    }

    @Test
    public void testFindGameByGameCode() {
        String gameCode = "testCode";
        Game expectedGame = new Game();
        when(gameRepository.findGameByCode(gameCode)).thenReturn(expectedGame);

        Game actualGame = gameService.findGameByGameCode(gameCode);
        verify(gameRepository).findGameByCode(gameCode);
        assertNotNull(actualGame);
        assertEquals(expectedGame, actualGame);
    }

    @Test
    public void testFindAllGamesByState() {
        GameState state = GameState.ONGOING;
        List<Game> expectedGames = Arrays.asList(new Game());
        when(gameRepository.findAllByState(state)).thenReturn(expectedGames);

        List<Game> actualGames = gameService.findAllByState(state);
        verify(gameRepository).findAllByState(state);
        
        assertEquals(expectedGames, actualGames);
    }

    @Test
    public void testFindStateByGameId() {
        Integer gameId = 1;
        Game game = new Game();
        game.setId(gameId);
        game.setState(GameState.ONGOING);
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        GameState actualState = gameService.findStateByGameId(gameId);
        verify(gameRepository).findById(gameId);
        assertEquals(GameState.ONGOING, actualState);
    }

    @Test
    public void testSave() {
        Game gameToSave = new Game();
        when(gameRepository.save(gameToSave)).thenReturn(gameToSave);

        Game savedGame = gameService.save(gameToSave);

        verify(gameRepository).save(gameToSave);
        assertEquals(gameToSave, savedGame);
    }

    @Test
    void shouldCountGamesByUserName() {
        Integer userId = 1;
        String username = "lolo";
        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        List<Game> games = Arrays.asList(new Game(), new Game());


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findByUserName(username)).thenReturn(games);

        Integer result = gameService.countGamesByUserName(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(gameRepository, times(1)).findByUserName(username);
        assertEquals(games.size(), result.intValue());

    }

    @Test
    void shouldGetTimePlayedByUsername() {
        Integer userId = 1;
        String username = "lolo";
        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        LocalDateTime startDate = LocalDateTime.now().minusHours(2);
        LocalDateTime endDate = LocalDateTime.now();
        Game game1 = new Game();
        game1.setStartDate(startDate);
        game1.setFinishDateTime(endDate);

        LocalDateTime startDate2 = LocalDateTime.now().minusHours(1);
        LocalDateTime finishDate2 = LocalDateTime.now();
        Game game2 = new Game();
        game2.setStartDate(startDate2);
        game2.setFinishDateTime(finishDate2);

        List<Game> games = Arrays.asList(game1, game2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findByUserName(username)).thenReturn(games);

        Double result = gameService.timePlayedByUserName(userId);

        double expectedTime = ChronoUnit.MINUTES.between(startDate, endDate) + ChronoUnit.MINUTES.between(startDate2, finishDate2);

        verify(userRepository, times(1)).findById(userId);
        verify(gameRepository, times(1)).findByUserName(username);
        assertEquals(expectedTime, result, 0.001);
    }

    @Test
    void shouldCountCompetitiveGamesByUserName() {
        Integer userId = 1;
        String username = "lolo";
        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        Game game1 = new Game();
        game1.setIsCompetitive(true);

        Game game2 = new Game();
        game2.setIsCompetitive(false);

        Game game3 = new Game();
        game3.setIsCompetitive(true);


        List<Game> games = Arrays.asList(game1, game2, game3);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findByUserName(username)).thenReturn(games);


        Integer result = gameService.countCompetitiveGamesByUserName(userId);

        long expectedCount = games.stream().filter(Game::getIsCompetitive).count();

        verify(userRepository, times(1)).findById(userId);
        verify(gameRepository, times(1)).findByUserName(username);
        assertEquals(expectedCount, result.longValue());

    }

    @Test
    void shouldCountNotCompetitiveGamesByUserName() {
        Integer userId = 1;
        String username = "lolo";
        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        Game game1 = new Game();
        game1.setIsCompetitive(true);


        Game game2 = new Game();
        game2.setIsCompetitive(false);

        Game game3 = new Game();
        game3.setIsCompetitive(true);


        List<Game> games = Arrays.asList(game1, game2, game3);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findByUserName(username)).thenReturn(games);


        Integer result = gameService.countNotCompetitiveGamesByUserName(userId);


        long expectedCount = games.stream().filter(game -> !game.getIsCompetitive()).count();


        verify(userRepository, times(1)).findById(userId);
        verify(gameRepository, times(1)).findByUserName(username);
        assertEquals(expectedCount, result.longValue());
    }

    @Test
    void testJuegaTuPrimeraPartida() {
        Integer userId = 1;
        String username = "lolo";
        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        List<Game> emptyGameList = Collections.emptyList();
        List<Game> nonEmptyGameList = Collections.singletonList(new Game());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(gameRepository.findByUserName(username)).thenReturn(emptyGameList);
        assertFalse(gameService.juegaTuPrimeraPartida(userId));

        when(gameRepository.findByUserName(username)).thenReturn(nonEmptyGameList);
        assertTrue(gameService.juegaTuPrimeraPartida(userId));

        verify(userRepository, times(2)).findById(userId);
        verify(gameRepository, times(2)).findByUserName(username);
    }

    @Test
    void testJuega5Partidas() {
        Integer userId = 1;
        String username = "lolo";
        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        List<Game> lessThan5GamesList = Collections.singletonList(new Game());
        List<Game> exactly5GamesList = Collections.nCopies(5, new Game());
        List<Game> moreThan5GamesList = Collections.nCopies(6, new Game());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(gameRepository.findByUserName(username)).thenReturn(lessThan5GamesList);
        assertFalse(gameService.juega5partidas(userId));

        when(gameRepository.findByUserName(username)).thenReturn(exactly5GamesList);
        assertTrue(gameService.juega5partidas(userId));
        
        when(gameRepository.findByUserName(username)).thenReturn(moreThan5GamesList);
        assertTrue(gameService.juega5partidas(userId));

        verify(userRepository, times(3)).findById(userId);
        verify(gameRepository, times(3)).findByUserName(username);
    }

    @Test
    void testJuegaMasDe100Min() {
        Integer userId = 1;
        String username = "lolo";
        Long tiempoMenosDe100 = (long) 90;
        Long tiempoMasDe100 = (long) 110.0;

        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        Game game1 = new Game();
        game1.setStartDate(LocalDateTime.now().minusMinutes(tiempoMenosDe100));
        game1.setFinishDateTime(LocalDateTime.now());

        Game game2 = new Game();
        game2.setStartDate(LocalDateTime.now().minusMinutes(tiempoMasDe100));
        game2.setFinishDateTime(LocalDateTime.now());

        List<Game> gamesMenosDe100 = Collections.singletonList(game1);
        List<Game> gamesMasDe100 = Collections.singletonList(game2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(gameRepository.findByUserName(username)).thenReturn(gamesMenosDe100);
        assertFalse(gameService.juegaMasde100min(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(gameRepository, times(1)).findByUserName(username);

        when(gameRepository.findByUserName(username)).thenReturn(gamesMasDe100);
        assertTrue(gameService.juegaMasde100min(userId));

        verify(userRepository, times(2)).findById(userId);
        verify(gameRepository, times(2)).findByUserName(username);
    }
    
}
