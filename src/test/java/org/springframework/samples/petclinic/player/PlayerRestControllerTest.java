package org.springframework.samples.petclinic.player;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureTestDatabase
public class PlayerRestControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerRestController playerController;

    private MockMvc mockMvc;

    @Test
    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    void adminShouldFindAllPlayers() throws Exception {
        Player player1 = new Player();
        Player player2 = new Player();
        List<Player> players = Arrays.asList(player1, player2);

        when(playerService.findAll()).thenReturn(players);

        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        mockMvc.perform(get("/api/v1/players"))
               .andExpect(status().isOk());
    }

    @Test
    void testFindById() throws Exception {
        int playerId = 1;
        Player player = new Player();

        when(playerService.getPlayerById(anyInt())).thenReturn(player);

        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        mockMvc.perform(get("/api/v1/players/{playerId}", playerId))
               .andExpect(status().isOk());
    }

    @Test
    void testFindByIdPlayerNotFound() throws Exception {
        int playerId = 2;

        when(playerService.getPlayerById(anyInt())).thenReturn(null);

        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        mockMvc.perform(get("/api/v1/players/{playerId}", playerId))
               .andExpect(status().isNotFound());
    }

    @Test
    void testGamesPlayedBy() throws Exception {
        int playerId = 1;
        Player player = new Player();
        List<Game> games = Arrays.asList(new Game(), new Game());

        when(playerService.getPlayerById(anyInt())).thenReturn(player);
        when(playerService.findGamesByPlayer(player)).thenReturn(games);

        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        mockMvc.perform(get("/api/v1/players/{playerId}/gamesPlayed", playerId))
               .andExpect(status().isOk());
    }

    @Test
    void testDeletePlayer() throws Exception {
        int playerId = 1;

        when(playerService.getPlayerById(playerId)).thenReturn(new Player());

        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        mockMvc.perform(delete("/api/v1/players/{playerId}", playerId))
               .andExpect(status().isOk());
    }

    @Test
    void testDeleteNonExistingPlayer() throws Exception {
        int nonExistingPlayerId = 2;

        when(playerService.getPlayerById(nonExistingPlayerId)).thenReturn(null);

        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        mockMvc.perform(delete("/api/v1/players/{playerId}", nonExistingPlayerId))
               .andExpect(status().isNotFound());
    }
    
    
}
