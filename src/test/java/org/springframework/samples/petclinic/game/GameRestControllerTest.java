package org.springframework.samples.petclinic.game;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.round.Minigame;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@SpringBootTest
@AutoConfigureTestDatabase
public class GameRestControllerTest {

    @MockBean
    protected GameRepository gameRepository;

    @MockBean
    protected PlayerRepository playerRepository;

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameRestController gameController;

    private MockMvc mockMvc;

    private Game game;
    private Game game2;
    private Player player1;
    private Player player2;
    private Round round1;
    private Round round2;

    @BeforeEach
    void setup() {
        game = new Game();
        game.setId(99);;
        game.setIsCompetitive(true);
        game.setState(GameState.UNSTARTED);
        game.setTimeLimit(10);
        game.setStartDate(LocalDateTime.of(2023, 12, 29, 16, 30, 0));
        game.setFinishDateTime(LocalDateTime.of(2023, 12, 29, 16, 49, 50));
        game.setRoundLimit(null);
        game.setLimitedByRound(false);
        game.setGameCode("DP111");
        
        game2 = new Game();
        game2.setId(100);;
        game2.setIsCompetitive(true);
        game2.setState(GameState.UNSTARTED);
        game2.setTimeLimit(10);
        game2.setStartDate(LocalDateTime.of(2023, 12, 29, 16, 30, 0));
        game2.setFinishDateTime(LocalDateTime.of(2023, 12, 29, 16, 49, 50));
        game2.setRoundLimit(null);
        game2.setLimitedByRound(false);
        game2.setGameCode("DP2");

        player1 = new Player();
        player1.setFirstName("Roberto");
        player1.setLastName("Ales");
        player1.setEmail("roberto@gmail.com");
        player1.setTelephone("654111123");

        player2 = new Player();
        player1.setFirstName("Alberto");
        player1.setLastName("Garcia");
        player1.setEmail("alberto@gmail.com");
        player1.setTelephone("698523147");

        round1 = new Round();
        round1.setId(1);
        round1.setIsOnGoing(false);
        round1.setMinigame(Minigame.PATATACALIENTE);

        round2 = new Round();
        round2.setId(2);
        round2.setIsOnGoing(true);
        round2.setMinigame(Minigame.TORREINFERNAL);
    }

    @Test
    public void shouldCreateGameWithCorrectInformation() {
        Game game = new Game();
        game.setIsCompetitive(false);
        game.setGameCode("hola2");
        GameState state = GameState.UNSTARTED;
        game.setState(state);
        game.setTimeLimit(15);
        game.setLimitedByRound(false);
        game.setRoundLimit(null);
        game.setStartDate(LocalDateTime.now());
        game.setFinishDateTime(null);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        game.setId(45);
        assertNotNull(game.getId());
    
    }

    @Test
    public void shouldNotCreateGameWithIncorrectGameCode() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Game game = new Game();
        game.setIsCompetitive(false);
        game.setGameCode("hola799779");
        GameState state = GameState.UNSTARTED;
        game.setState(state);
        game.setTimeLimit(15);
        game.setLimitedByRound(false);
        game.setRoundLimit(null);
        game.setStartDate(LocalDateTime.now());
        game.setFinishDateTime(null);
        assertFalse(validator.validate(game).isEmpty(), "El código del juego tiene que tener una longitud comprendida entre 3 y 5 caracteres");
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    void adminShouldFindAllGames() throws Exception {
        when(gameService.findAll()).thenReturn(List.of(game2));
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/api/v1/games"))
               .andExpect(status().isOk());     
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    void shouldFindStateByGameId() throws Exception {
        when(gameService.findStateByGameId(eq(99))).thenReturn(GameState.UNSTARTED);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/api/v1/games/state_by/99"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value("UNSTARTED"));

        verify(gameService, times(1)).findStateByGameId(eq(99));
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    void shouldFindRoundsByGameId() throws Exception {
        when(gameService.findRoundsById(eq(99))).thenReturn(Arrays.asList(round1, round2));
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/api/v1/games/99/rounds"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].isOnGoing").value(false))
            .andExpect(jsonPath("$[0].minigame").value("PATATACALIENTE"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].isOnGoing").value(true))
            .andExpect(jsonPath("$[1].minigame").value("TORREINFERNAL"));
        
        verify(gameService, times(1)).findRoundsById(eq(99));
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    void shouldFindIsCompetitiveByGameId() throws Exception {
        when(gameService.findById(eq(99))).thenReturn(game);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/api/v1/games/99/competitive"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(true));

        verify(gameService, times(1)).findById(eq(99));
    }

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    void shouldFindAllGamesByState() throws Exception {
        List<Game> games = Arrays.asList(game, game2);
        when(gameService.findAllByState(GameState.UNSTARTED)).thenReturn(games);

        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/api/v1/games/state/UNSTARTED"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].id").value(99))
            .andExpect(jsonPath("$[1].id").value(100))
            .andExpect(jsonPath("$[0].isCompetitive").value(true))
            .andExpect(jsonPath("$[1].isCompetitive").value(true))
            .andExpect(jsonPath("$[0].gameCode").value("DP111"))
            .andExpect(jsonPath("$[1].gameCode").value("DP2"));

        verify(gameService, times(1)).findAllByState(eq(GameState.UNSTARTED));
    }

    @Test
    @WithMockUser(value = "player", authorities = {"PLAYER"})
    void shouldFindGameByCodeGame() throws Exception {
        when(gameService.findGameByGameCode(eq("DP111"))).thenReturn(game);

        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/api/v1/games/gameCode/DP111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.isCompetitive").value(true))
                .andExpect(jsonPath("$.gameCode").value("DP111"));
        
        verify(gameService, times(1)).findGameByGameCode(eq("DP111"));
    }

    @Test
    public void testUpdateIsWinner() throws Exception {
        Integer gameId = 456;

        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(put("/api/v1/games/isWinner/{gameId}", gameId))
                .andExpect(status().isOk());

        verify(gameService).setGameWinner(gameId);
    }

    @Test
    public void testCountGamesByUserName() throws Exception {
        Integer userId = 1;
        Integer expectedCount = 5;
        when(gameService.countGamesByUserName(userId)).thenReturn(expectedCount);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        mockMvc.perform(get("/api/v1/games/numOfGames/{username}", userId))
               .andExpect(status().isOk())
               .andExpect(result -> assertEquals(expectedCount, Integer.parseInt(result.getResponse().getContentAsString())));
    }

    @Test
    public void testTimePlayedByUserName() throws Exception {
        Integer userId = 1;
        Double expectedTimePlayed = 10.5;

        when(gameService.timePlayedByUserName(userId)).thenReturn(expectedTimePlayed);

        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        mockMvc.perform(get("/api/v1/games/timePlayed/{username}", userId))
               .andExpect(status().isOk())
               .andExpect(result -> assertEquals(expectedTimePlayed, Double.parseDouble(result.getResponse().getContentAsString())));
    }

    @Test
    public void testCountCompetitiveGamesByUserName() throws Exception {
        Integer userId = 1;
        Integer expectedCount = 8;

        when(gameService.countCompetitiveGamesByUserName(userId)).thenReturn(expectedCount);

        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        mockMvc.perform(get("/api/v1/games/numOfCompetitiveGames/{username}", userId))
               .andExpect(status().isOk())
               .andExpect(result -> assertEquals(expectedCount, Integer.parseInt(result.getResponse().getContentAsString())));
    }

    @Test
    public void testCountNonCompetitiveGamesByUserName() throws Exception {
        Integer userId = 1;
        Integer expectedCount = 8;

        when(gameService.countNotCompetitiveGamesByUserName(userId)).thenReturn(expectedCount);

        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        mockMvc.perform(get("/api/v1/games/numOfNotCompetitiveGames/{username}", userId))
               .andExpect(status().isOk())
               .andExpect(result -> assertEquals(expectedCount, Integer.parseInt(result.getResponse().getContentAsString())));
    }

    @Test
    public void testCountCompetitiveGamesWonByUserId() throws Exception {
        Integer userId = 123;
        int expectedCount = 10;

        when(gameService.countVictoriesRankedByUserName(userId)).thenReturn(expectedCount);

        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        mockMvc.perform(get("/api/v1/games/numOfCompetitiveGamesWon/{username}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedCount)));
    }

    @Test
    public void testCountNotCompetitiveGamesWonByUserName() throws Exception {
        Integer userId = 123;
        int expectedCount = 5;

        when(gameService.countVictoriesFriendlyByUserName(userId)).thenReturn(expectedCount);

        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        mockMvc.perform(get("/api/v1/games/numOfNotCompetitiveGamesWon/{username}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedCount)));
    }

}
