package org.springframework.samples.petclinic.gameProperties;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureTestDatabase
public class GamePropertiesRestControllerTest {

    @Mock
    private GamePropertiesService gamePropertiesService;

    @InjectMocks
    private GamePropertiesController gamePropertiesRestController;

    private MockMvc mockMvc;

    @Test
    public void testGetAllGameProperties() throws Exception {
        GameProperties gameProperties1 = new GameProperties();
        gameProperties1.setGamePoints(20);

        GameProperties gameProperties2 = new GameProperties();
        gameProperties2.setGamePoints(30);

        List<GameProperties> gamePropertiesList = Arrays.asList(gameProperties1, gameProperties2);
        when(gamePropertiesService.findAll()).thenReturn(gamePropertiesList);

        mockMvc = MockMvcBuilders.standaloneSetup(gamePropertiesRestController).build();
        mockMvc.perform(get("/api/v1/gameProperties/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(gamePropertiesList.size()))
                .andExpect(jsonPath("$[0].gamePoints").value(20))
                .andExpect(jsonPath("$[1].gamePoints").value(30));        
    }

    @Test
    void shouldFindGamePropertiesByGameId() throws Exception {
        Integer gameId = 123;
        GameProperties gameProperties1 = new GameProperties();
        gameProperties1.setGamePoints(30);

        GameProperties gameProperties2 = new GameProperties();
        gameProperties2.setGamePoints(20);

        List<GameProperties> gamePropertiesList = Arrays.asList(gameProperties1, gameProperties2);
        when(gamePropertiesService.findGamePropertiesByGameId(gameId)).thenReturn(gamePropertiesList);

        mockMvc = MockMvcBuilders.standaloneSetup(gamePropertiesRestController).build();
        mockMvc.perform(get("/api/v1/gameProperties/games/{gameId}/id", gameId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(gamePropertiesList.size()))
                .andExpect(jsonPath("$[0].gamePoints").value(30))
                .andExpect(jsonPath("$[1].gamePoints").value(20));
    }

    @Test
    void shouldFindIfIsCreator() throws Exception {
        Integer gameId = 123;
        Integer playerId = 456;

        when(gamePropertiesService.findIfIsCreator(gameId, playerId)).thenReturn(true);

        mockMvc = MockMvcBuilders.standaloneSetup(gamePropertiesRestController).build();
        mockMvc.perform(get("/api/v1/gameProperties/creator/{gameId}/{playerId}", gameId, playerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void shouldSumPoints() throws Exception {
        Integer gameId = 123;
        Integer playerId = 456;

        ResponseEntity<GameProperties> responseEntity = ResponseEntity.ok().build();
        when(gamePropertiesService.sumPoints(gameId, playerId)).thenReturn(responseEntity);

        mockMvc = MockMvcBuilders.standaloneSetup(gamePropertiesRestController).build();
        mockMvc.perform(put("/api/v1/gameProperties/gamePoints/{gameId}/player/{playerId}", gameId, playerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    
}
