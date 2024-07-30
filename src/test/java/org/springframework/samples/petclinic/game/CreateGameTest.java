package org.springframework.samples.petclinic.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment =  WebEnvironment.RANDOM_PORT)
public class CreateGameTest {
    
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    static final String BASE_URL = "/api/v1/games";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(SecurityMockMvcConfigurers.springSecurity())
                    .build();
    }

    private Game createValidGame() {
        Game game = new Game();
        game.setIsCompetitive(false);
        game.setGameCode("hola2");
        GameState state = GameState.UNSTARTED;
        game.setState(state);
        return game;
    }

    @Test
    @Transactional
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void feasibleGameCreationTest() throws JsonProcessingException, Exception {
        Game game = createValidGame();
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(game))).andExpect(status().isCreated());
        assertThat(gameService.findGameByGameCode(game.getGameCode()));
    }

}
